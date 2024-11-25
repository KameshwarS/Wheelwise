package com.infosys.Wheelwise.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.Wheelwise.Model.Image;
import com.infosys.Wheelwise.Model.Vehicle;
import com.infosys.Wheelwise.Services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController

public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    // Endpoint to get all vehicles
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // Endpoint to get available vehicles based on filters and requested time range
    @GetMapping("/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicles(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String companyName,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmissionType,
            @RequestParam(required = false) Integer numofseats,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ) {
        List<Vehicle> availableVehicles = vehicleService.findAvailableVehiclesWithFilters(
                location, startTime, endTime, type, companyName, fuelType, transmissionType, numofseats, minPrice, maxPrice
        );
        return ResponseEntity.ok(availableVehicles);
    }

    @PostMapping("/addVehicle")
    public ResponseEntity<String> createVehicle(@RequestParam("vehicleData") String vehicleData,
                                                @RequestParam("image") MultipartFile[] images) {
        // Parse vehicleData to create a Vehicle object
        ObjectMapper objectMapper = new ObjectMapper();
        Vehicle vehicle;
        try {
            vehicle = objectMapper.readValue(vehicleData, Vehicle.class);
        } catch (IOException e) {
            // Handle parsing error
            return ResponseEntity.badRequest().body("Invalid vehicle data");
        }

        // Save the image to the file system


        for (MultipartFile image : images) {
            String imagePath = saveImageToFile(image);
            vehicle.getImagePaths().add(imagePath);

            // Create Image object and associate it with the vehicle
            Image vehicleImage = new Image();
            vehicleImage.setImagePath(imagePath);
            vehicleImage.setVehicle(vehicle);
            vehicle.getImages().add(vehicleImage);
        }



        // Save the vehicle and image to the database
        vehicleService.saveVehicle(vehicle);

        return ResponseEntity.ok("Vehicle Added");
    }

    private String saveImageToFile(MultipartFile image) {
        // Set the desired path to store the images
        String uploadDir = "C:\\Users\\kames\\WheelwiseImageFinal"; // Adjust the path as needed

        // Create the directory if it doesn't exist
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                // Handle exception, e.g., log the error and return an error response
                e.printStackTrace();
                return null;
            }
        }

        // Create the full file path
        Path imagePath = Paths.get(uploadDir, image.getOriginalFilename());

        try {
            Files.write(imagePath, image.getBytes());
            return imagePath.toString();
        } catch (IOException e) {
            // Handle exception, e.g., log the error and return an error response
            e.printStackTrace();
            return null;
        }
    }

}
