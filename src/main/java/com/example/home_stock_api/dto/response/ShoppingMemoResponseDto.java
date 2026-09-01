package com.example.home_stock_api.dto.response;

import java.time.OffsetDateTime;

public record ShoppingMemoResponseDto(
        Long id,
        String name,
        OffsetDateTime createdAt
) {
}
