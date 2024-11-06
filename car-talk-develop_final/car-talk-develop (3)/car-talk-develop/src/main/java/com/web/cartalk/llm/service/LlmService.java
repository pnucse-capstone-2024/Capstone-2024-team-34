package com.web.cartalk.llm.service;

import com.web.cartalk.llm.client.LlmClient;
import com.web.cartalk.llm.dto.LlmRequest;
import com.web.cartalk.llm.dto.LlmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class LlmService {

    private final LlmClient llmClient;

    public LlmResponse.Answer question(@Validated LlmRequest.Question question) {
        return llmClient.question(question);
    }
}
