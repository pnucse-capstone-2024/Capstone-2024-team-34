package com.web.cartalk.user;

import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users",
        indexes = {
                @Index(name = "users_loginId_idx", columnList = "loginId"),
                @Index(name = "users_email_idx", columnList = "email")
        })
public class User extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 40, nullable = false, unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 40)
    @ColumnDefault("'회원'")
    private String name;

    @ColumnDefault("false")
    private Boolean gender;

    @ColumnDefault("'2000-01-01'")
    private LocalDate birth;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ROLE_PENDING'")
    @Column(length = 50)
    private Role role;

    @Builder
    public User(Long id, String loginId, String password, String name, Boolean gender, LocalDate birth, String email, Role role) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.birth = birth;
        this.email = email;
        this.role = role;
    }

    public void updateLoginId(String loginId) {
        this.loginId = loginId;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGender(Boolean gender) {
        this.gender = gender;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateRole(Role role) {
        this.role = role;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

}

