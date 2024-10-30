package com.web.cartalk.favor;

import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import com.web.cartalk.user.User;
import com.web.cartalk.vehicle.Vehicle;
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
@Table(name = "favor",
        indexes = {
                @Index(name = "favor_user_id_idx", columnList = "user_id")
        })
public class Favor extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicle vehicle;

    @ColumnDefault("now()")
    private LocalDateTime createdAt;

    @Builder
    public Favor(Long id, User user, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
    }
}
