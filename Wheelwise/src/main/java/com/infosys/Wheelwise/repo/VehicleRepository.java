package com.infosys.Wheelwise.repo;

import com.infosys.Wheelwise.Model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


    @Repository
    public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

        @Query("SELECT v FROM Vehicle v WHERE v.id NOT IN (SELECT b.vehicle.id FROM Booking b WHERE b.status = 'CONFIRMED')")
        List<Vehicle> findAllAvailableVehicles();

    }


