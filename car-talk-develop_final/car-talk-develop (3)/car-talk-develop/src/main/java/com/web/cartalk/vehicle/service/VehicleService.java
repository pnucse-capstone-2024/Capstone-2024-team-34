package com.web.cartalk.vehicle.service;

import com.web.cartalk.vehicle.Vehicle;
import com.web.cartalk.vehicle.dto.VehicleResponse;
import com.web.cartalk.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class VehicleService {
    final private VehicleRepository vehicleRepository;

    @Transactional
    public VehicleResponse.FindAllDto getRandomVehicles(Integer size) {
        List<Vehicle> allVehicle = vehicleRepository.findAll();

        Collections.shuffle(allVehicle);
        List<Vehicle> randomVehicle = allVehicle.subList(0, Math.min(size, allVehicle.size()));

        return VehicleResponse.FindAllDto.of(randomVehicle);
    }

}
