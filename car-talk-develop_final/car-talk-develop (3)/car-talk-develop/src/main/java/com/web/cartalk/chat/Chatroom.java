package com.web.cartalk.chat;

import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import com.web.cartalk.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "chatroom",
        indexes = {
                @Index(name = "chatroom_user_id_idx", columnList = "user_id")
        })
public class Chatroom extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Chatroom(Long id, String title, User user) {
        this.id = id;
        this.title = title;
        this.user = user;
    }

    public void updateTitle(String title) {
        this.title = title;
    }
}
