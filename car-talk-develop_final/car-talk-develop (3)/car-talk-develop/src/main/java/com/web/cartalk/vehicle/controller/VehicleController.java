package com.web.cartalk.vehicle.controller;

import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.vehicle.dto.VehicleResponse;
import com.web.cartalk.vehicle.service.VehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class VehicleController {

    final private VehicleService vehicleService;

    @GetMapping("/vehicle/random")
    public ResponseEntity<?> getRandomVehicles(@RequestParam(value = "size", defaultValue = "30")
                                               Integer size) {
        VehicleResponse.FindAllDto responseDto = vehicleService.getRandomVehicles(size);
        ApiUtils.Response<?> response = ApiUtils.success(responseDto);
        return ResponseEntity.ok().body(response);
    }
}
