package com.example.home_stock_api.dto.request;

import com.example.home_stock_api.entity.ItemCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record ItemCreateRequestDto(
        @NotBlank(message = "Item名は必須です")
        @Size(max = 100, message = "100文字以内で入力してください")
        String name,

        @NotNull
        @PositiveOrZero
        Integer quantity,

        @NotNull
        @PositiveOrZero
        Integer minQuantity,

        Long locationId,

        @NotNull
        ItemCategory category,

        String memo,

        LocalDate expirationDate


) {


}
