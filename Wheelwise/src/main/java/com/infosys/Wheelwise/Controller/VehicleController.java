package com.infosys.Wheelwise.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosys.Wheelwise.Model.Image;
import com.infosys.Wheelwise.Model.Vehicle;
import com.infosys.Wheelwise.Services.VehicleService;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController

public class VehicleController {

    @Autowired
    private ResourceLoader resourceLoader;

    private static final Logger logger = LoggerFactory.getLogger(VehicleController.class);


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
        String uploadDir = "src/main/java/com/infosys/Wheelwise/ImageDir"; // Adjust the path as needed

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
    @GetMapping("/images/{imagePath}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imagePath) {
        try {
            byte[] imageData = getImageData(imagePath);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Adjust content type based on image format
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageData);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private byte[] getImageData(String imagePath) throws IOException {
        Resource resource = resourceLoader.getResource(imagePath);
        try {
            return IOUtils.toByteArray(resource.getInputStream());
        } catch (FileNotFoundException e) {
            logger.error("Image not found: {}", imagePath);
            try {
                throw new ChangeSetPersister.NotFoundException();
            } catch (ChangeSetPersister.NotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException e) {
            logger.error("Error reading image: {}", e.getMessage());
            throw new RuntimeException("Error fetching image");
        }
    }




}
