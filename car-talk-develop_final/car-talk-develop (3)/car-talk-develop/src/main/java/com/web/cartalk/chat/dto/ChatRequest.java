package com.web.cartalk.chat.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    public record question(

            @NotBlank
            String question
    ) {

    }
}
