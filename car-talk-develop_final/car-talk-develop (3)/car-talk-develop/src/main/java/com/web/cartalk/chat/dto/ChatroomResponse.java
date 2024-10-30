package com.web.cartalk.chat.dto;

import com.web.cartalk.chat.Chatroom;
import com.web.cartalk.chat.message.Message;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ChatroomResponse {
    public record GetChatroomDto(
            List<ChatroomDto> chatrooms
    ) {
        public static GetChatroomDto of(List<Chatroom> chatrooms) {
            return new GetChatroomDto(chatrooms.stream()
                    .map(ChatroomDto::new)
                    .toList());
        }

        public record ChatroomDto(
                Long id,
                String title,
                LocalDateTime createdAt,
                LocalDateTime updatedAt
        ) {
            public ChatroomDto(Chatroom chatroom) {
                this(chatroom.getId(), chatroom.getTitle(), chatroom.getCreatedAt(), chatroom.getUpdatedAt());
            }
        }
    }

    public record getMessagesDto(
            List<MessageDto> messages
    ) {
        // messages 리스트에서 content와 isFromChatbot가져오는 메소드
        public static getMessagesDto of(List<Message> messageList) {
            return new getMessagesDto(messageList.stream()
                    .map(message ->
                            new MessageDto(
                                    message.getId(),
                                    message.getContent(),
                                    message.isFromChatbot(),
                                    message.getCreatedAt()
                            )
                    )
                    .toList());
        }

        public record MessageDto(
                Long id,
                String content,
                Boolean isFromChatbot,
                LocalDateTime createdAt
        ) {

        }
    }

    public record CreateChatroomDto(
            Long chatroomId
    ) {
        public CreateChatroomDto(Chatroom chatroom) {
            this(chatroom.getId());
        }
    }
}
