package com.web.cartalk.favor.dto;

import java.util.List;

public class FavorRequest {
    public record SaveUserVehiclePreferenceDto(List<Long> vehicleIds) {

    }
}
