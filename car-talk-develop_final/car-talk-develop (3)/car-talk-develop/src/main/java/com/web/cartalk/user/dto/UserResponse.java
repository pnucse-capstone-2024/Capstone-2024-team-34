package com.web.cartalk.user.dto;

import com.web.cartalk.favor.Favor;
import com.web.cartalk.user.User;

import java.time.LocalDateTime;
import java.util.List;

public class UserResponse {
    public record GetUserDto(
            Long id,
            String loginId,
            String name,
            Boolean gender,
            String birth,
            String email,
            List<FavorDto> favors,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
        public GetUserDto(User user, List<Favor> favors) {
            this(
                    user.getId(),
                    user.getLoginId(),
                    user.getName(),
                    user.getGender(),
                    user.getBirth().toString(),
                    user.getEmail(),
                    favors.stream()
                            .map(FavorDto::new)
                            .toList(),
                    user.getCreatedAt(),
                    user.getUpdatedAt()
            );
        }

        public record FavorDto(
                Long VehicleId,
                String VehicleName
        ) {
            public FavorDto(Favor favor) {
                this(favor.getVehicle().getId(), favor.getVehicle().getName());
            }
        }
    }

    public record JoinUserDto(
            Long id,
            String email
    ) {
        public JoinUserDto(
                User user
        ) {
            this(user.getId(), user.getEmail());
        }
    }

    public record FindUserIdDto(
            String loginId
    ) {
        public FindUserIdDto(String loginId) {
            this.loginId = loginId.substring(0, 2) + "***" + loginId.substring(loginId.length() - 1);
        }

    }


    public record TokensDto(
            String access,
            String refresh
    ) {
    }
}
