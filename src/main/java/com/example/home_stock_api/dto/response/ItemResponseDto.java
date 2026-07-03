package com.example.home_stock_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class ItemResponseDto {
    private Long id;
    private String name;
    private Integer quantity;
    private Integer minQuantity;
    private String category;
    private Long locationId;
    private String locationName;
    private LocalDate expirationDate;
    private String memo;
}
