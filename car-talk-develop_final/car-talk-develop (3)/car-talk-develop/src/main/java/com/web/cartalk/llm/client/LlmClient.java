package com.web.cartalk.llm.client;

import com.web.cartalk.llm.dto.LlmRequest;
import com.web.cartalk.llm.dto.LlmResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "llmClient", url = "${llm.end-point}")
public interface LlmClient {

    @PostMapping("/question")
    LlmResponse.Answer question(@RequestBody LlmRequest.Question question);
}
