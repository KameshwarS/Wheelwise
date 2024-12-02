package com.infosys.Wheelwise.Services;


import com.infosys.Wheelwise.Model.Booking;
import com.infosys.Wheelwise.Model.Vehicle;
import com.infosys.Wheelwise.repo.BookingRepository;
import com.infosys.Wheelwise.repo.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private BookingRepository bookingRepository;

    // Method to fetch all vehicles
    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    // Method to check if a vehicle is available for the given time range
    private boolean isVehicleAvailable(Vehicle vehicle, LocalDateTime startTime, LocalDateTime endTime) {
        // Get all confirmed bookings for the vehicle
        List<Booking> bookings = bookingRepository.findByVehicle(vehicle);

        // Loop through all bookings to check for any time overlap
        for (Booking booking : bookings) {
            LocalDateTime bookingStart = booking.getStartDate();
            LocalDateTime bookingEnd = booking.getEndDate();

            // If the requested time range overlaps with any booking, return false
            if (startTime.isBefore(bookingEnd) && endTime.isAfter(bookingStart)) {
                return false; // Vehicle is not available due to time overlap
            }
        }

        // If no overlap, vehicle is available
        return true;
    }

    // Method to filter vehicles based on the time range and other filters
    public List<Vehicle> findAvailableVehiclesWithFilters(String location, LocalDateTime startTime, LocalDateTime endTime,
                                                          String type, String companyName, String fuelType,
                                                          String transmissionType, Integer numofseats, Double minPrice, Double maxPrice) {
        // Fetch all vehicles from the repository
        List<Vehicle> vehicles = vehicleRepository.findAll();

        BigDecimal minPriceDecimal = (minPrice != null) ? BigDecimal.valueOf(minPrice) : null;
        BigDecimal maxPriceDecimal = (maxPrice != null) ? BigDecimal.valueOf(maxPrice) : null;

        // Filter vehicles based on availability (using isVehicleAvailable)
        return vehicles.stream()
                .filter(vehicle -> isVehicleAvailable(vehicle, startTime, endTime)) // Check availability
                .filter(vehicle -> location == null || vehicle.getLocation().equalsIgnoreCase(location))
                .filter(vehicle -> type == null || vehicle.getType().equalsIgnoreCase(type))
                .filter(vehicle -> companyName == null || vehicle.getCompanyName().equalsIgnoreCase(companyName))
                .filter(vehicle -> fuelType == null || vehicle.getFuelType().equalsIgnoreCase(fuelType))
                .filter(vehicle -> transmissionType == null || vehicle.getTransmissionType().equalsIgnoreCase(transmissionType))
                .filter(vehicle -> numofseats == null || vehicle.getCapacity() == numofseats) // Use '==' for primitive types
                .filter(vehicle -> minPriceDecimal == null || vehicle.getPricePerDay().compareTo(minPriceDecimal) >= 0)
                .filter(vehicle -> maxPriceDecimal == null || vehicle.getPricePerDay().compareTo(maxPriceDecimal) <= 0)
                .collect(Collectors.toList());
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }


    // Existing method to get vehicles by type
    public List<Vehicle> getVehiclesByType(String type) {
        List<Vehicle> vehicles = getAllVehicles();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    // New method to get vehicles by company name
    public List<Vehicle> getVehiclesByCompanyName(String companyName) {
        return vehicleRepository.findByCompanyName(companyName);
    }

    // New method to get vehicles by fuel type
    public List<Vehicle> getVehiclesByFuelType(String fuelType) {
        List<Vehicle> vehicles = getAllVehicles();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getFuelType().equalsIgnoreCase(fuelType))
                .collect(Collectors.toList());
    }

    // New method to get vehicles by transmission type
    public List<Vehicle> getVehiclesByTransmissionType(String transmissionType) {
        List<Vehicle> vehicles = getAllVehicles();
        return vehicles.stream()
                .filter(vehicle -> vehicle.getTransmissionType().equalsIgnoreCase(transmissionType))
                .collect(Collectors.toList());
    }

    // Method to update the rating of a vehicle
    public void updateVehicleRating(long vehicleId, double rating) {
        // Fetch the vehicle by ID
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElse(null);

        if (vehicle != null) {
            // Set the new rating
            vehicle.setRating((byte) rating);

            // Save the updated vehicle back to the database
            vehicleRepository.save(vehicle);
        }
    }
}

