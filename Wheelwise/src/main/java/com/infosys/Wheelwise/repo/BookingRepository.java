package com.infosys.Wheelwise.repo;

import com.infosys.Wheelwise.Model.Booking;
import com.infosys.Wheelwise.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByVehicle(Vehicle vehicle); // Fetch bookings for a specific vehicle
}
