package com.web.cartalk.chat.message;

import com.web.cartalk.chat.Chatroom;
import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "message_tb",
        indexes = {
                @Index(name = "message_chatroom_id_idx", columnList = "chatroom_id")
        })
public class Message extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Chatroom chatroom;

    @Column(nullable = false)
    private boolean isFromChatbot;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String uuid;

    @Builder
    public Message(Long id, Chatroom chatroom, boolean isFromChatbot, String content, String uuid) {
        this.id = id;
        this.chatroom = chatroom;
        this.isFromChatbot = isFromChatbot;
        this.content = content;
        this.uuid = uuid;
    }
}
