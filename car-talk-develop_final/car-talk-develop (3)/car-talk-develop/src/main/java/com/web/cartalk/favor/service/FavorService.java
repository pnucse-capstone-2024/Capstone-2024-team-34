package com.web.cartalk.favor.service;

import com.web.cartalk.core.errors.exception.Exception400;
import com.web.cartalk.core.errors.exception.Exception404;
import com.web.cartalk.favor.Favor;
import com.web.cartalk.favor.dto.FavorRequest;
import com.web.cartalk.favor.repository.FavorRepository;
import com.web.cartalk.user.User;
import com.web.cartalk.user.repository.UserRepository;
import com.web.cartalk.vehicle.Vehicle;
import com.web.cartalk.vehicle.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class FavorService {

    final private FavorRepository favorRepository;
    final private VehicleRepository vehicleRepository;
    final private UserRepository userRepository;
    private List<Long> vehicleIds;


    @Transactional
    public void saveUserVehiclePreference(Long id, FavorRequest.SaveUserVehiclePreferenceDto requestDto) {
        List<Long> vehicleIds = requestDto.vehicleIds();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception404("해당 회원이 존재하지 않습니다"));

        List<Vehicle> vehicles = vehicleRepository.findByIdIn(vehicleIds);
        if (vehicleIds.size() != vehicles.size()) throw new Exception400("해당 차량이 존재하지 않습니다.");
        List<Favor> favors = vehicles.stream()
                .map(vehicle -> Favor.builder()
                        .user(user)
                        .vehicle(vehicle)
                        .build())
                .collect(Collectors.toList());

        favorRepository.saveAll(favors);
        log.info("사용자의 선호 차량 정보가 저장되었습니다. 사용자 ID: {}, 차량 ID: {}", id, vehicleIds);
    }

    /*@Transactional
public void saveUserVehiclePreference(Long id, FavorRequest.SaveUserVehiclePreferenceDto requestDto) {
        // 1. 사용자 ID를 기반으로 사용자 정보를 가져옵니다.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 회원이 존재하지 않습니다."));

        List<Long> storedVehicleIds = favorRepository.findVehicleIdsByUserId(user.getId());

        List<FavorRequest.VehicleDto> vehicleDtos = requestDto.getVehicleDtos();
        for (FavorRequest.VehicleDto vehicleDto : vehicleDtos) {
            Long vehicleId = vehicleDto.getId();
            boolean favor = vehicleDto.isFavor();

            if (storedVehicleIds.contains(vehicleId)) {

                if (!favor) {
                    storedVehicleIds.remove(vehicleId);
                }
            } else {

                if (favor) {
                    storedVehicleIds.add(vehicleId);
                }
            }
            favorRepository.saveUserVehiclePreferences(user.getId(), storedVehicleIds);

            log.info("사용자의 선호 차량 정보가 저장되었습니다. 사용자 ID: {}, 차량 ID: {}", id, storedVehicleIds);
        }
    }*/
}






