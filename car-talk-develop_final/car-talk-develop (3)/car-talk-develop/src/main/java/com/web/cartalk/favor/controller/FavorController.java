package com.web.cartalk.favor.controller;

import com.web.cartalk.core.security.CustomUserDetails;
import com.web.cartalk.core.utils.ApiUtils;
import com.web.cartalk.favor.dto.FavorRequest;
import com.web.cartalk.favor.service.FavorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FavorController {

    final private FavorService favorService;

    @PostMapping("/favors")
    public ResponseEntity<?> saveUserVehiclePreference(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                       @RequestBody @Valid FavorRequest.SaveUserVehiclePreferenceDto requestDto,
                                                       Errors errors) {
        favorService.saveUserVehiclePreference(userDetails.getId(), requestDto);
        ApiUtils.Response<?> response = ApiUtils.success(requestDto);
        return ResponseEntity.ok().body(response);
    }
}
