package com.web.cartalk.emailVerification;

import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "email_verification",
        indexes = {
                @Index(name = "email_verification_email_idx", columnList = "email")
        })
public class EmailVerification extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String verificationCode;

    @Builder
    public EmailVerification(Long id, String email, String verificationCode) {
        this.id = id;
        this.email = email;
        this.verificationCode = verificationCode;
    }
}
