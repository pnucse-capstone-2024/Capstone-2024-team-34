package com.web.cartalk.vehicle;

import com.web.cartalk.core.supporter.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vehicle")
public class Vehicle extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String link;

    @Column(length = 200, nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(length = 50, nullable = false)
    private String fuel;

    @Column(nullable = false)
    private int mileage;

    @Column(length = 50, nullable = false)
    private String years;

    @Column(length = 50, nullable = false)
    private String brand;

    @Column(length = 100, nullable = false)
    private String imageUrl;

    @Builder
    public Vehicle(Long id, String link, String name, int price, String fuel, int mileage, String years, String brand, String imageUrl) {
        this.id = id;
        this.link = link;
        this.name = name;
        this.price = price;
        this.fuel = fuel;
        this.mileage = mileage;
        this.years = years;
        this.brand = brand;
        this.imageUrl = imageUrl;
    }
}
