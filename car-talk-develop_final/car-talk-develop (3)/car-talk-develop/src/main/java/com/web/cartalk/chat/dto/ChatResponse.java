package com.web.cartalk.chat.dto;

import java.time.LocalDateTime;

public class ChatResponse {
    public record Answer(
            Long chatroomId,
            String question,
            LocalDateTime questionCreatedAt,
            String answer,
            LocalDateTime answerCreatedAt,
            Long currentCursor
    ) {
    }
}
