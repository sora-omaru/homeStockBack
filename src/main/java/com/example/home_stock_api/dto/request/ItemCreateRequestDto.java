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

        @NotNull(message = "数量は必須です")
        @PositiveOrZero(message = "数量は0以上で入力してください")
        Integer quantity,

        @NotNull(message = "最低在庫数は必須です")
        @PositiveOrZero(message = "最低在庫数は0以上で入力してください")
        Integer minQuantity,

        Long locationId,

        @NotNull(message = "カテゴリは必須です")
        ItemCategory category,

        @Size(max = 1000, message = "メモは1000文字以内で入力してください")
        String memo,

        LocalDate expirationDate

) {
}