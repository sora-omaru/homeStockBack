package com.example.home_stock_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",nullable = false)
    private UserEntity user;

    @Column(nullable = false,length = 100)
    private  String name;

    @Column(nullable = false)
    @NotNull
    @Min(0)
    private Integer quantity;

    @Column(nullable = false,length = 20)
    @NotNull
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @Min(0)
    @Column(name = "min_quantity",nullable = false)
    @NotNull
    private Integer minQuantity=0;

    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    @Column(name = "memo")
    private String memo;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        OffsetDateTime now = OffsetDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public  void preUpdate(){
        this.updatedAt = OffsetDateTime.now();
    }

}
