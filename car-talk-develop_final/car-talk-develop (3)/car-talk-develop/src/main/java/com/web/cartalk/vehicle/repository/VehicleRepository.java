package com.web.cartalk.vehicle.repository;

import com.web.cartalk.vehicle.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByIdIn(List<Long> ids);
}
