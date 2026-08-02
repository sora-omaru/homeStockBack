package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateItemQuantityRequestDto (
        @NotNull(message = "数量は必須です")
        @PositiveOrZero(message = "数量は0以上で入力してください")
        Integer quantity
){}