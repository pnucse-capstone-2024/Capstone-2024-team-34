package com.web.cartalk.vehicle.dto;

import com.web.cartalk.vehicle.Vehicle;

import java.util.List;


public class VehicleResponse {

    public record FindAllDto(List<VehicleDto> vehicles) {
        public static FindAllDto of(List<Vehicle> vehicleList) {
            return new FindAllDto(vehicleList.stream()
                    .map(vehicle -> new VehicleDto(vehicle.getId(), vehicle.getName(), vehicle.getImageUrl()))
                    .toList());
        }

        public record VehicleDto(Long id, String name, String imageUrl) {
        }
    }
}
