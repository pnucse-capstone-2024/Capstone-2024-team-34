package com.web.cartalk.chat.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChatroomRequest {
    public record ChangeTitleDto(
            @NotNull(message = "제목을 반드시 입력해야 합니다.")
            @Size(max = 50, message = "최대 50자까지 입니다.")
            @Pattern(regexp = "^[\\w.가-힣]+$", message = "영문, 숫자, _, ., 한글만 가능합니다.")
            String title
    ) {
    }
}
