package com.web.cartalk.llm.dto;

import jakarta.validation.constraints.NotBlank;

public class LlmRequest {

    public record Question(

            @NotBlank
            String question
    ) {
    }
}
