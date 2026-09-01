package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ShoppingMemoCreateRequestDto(
        @NotBlank(message = "買い物メモ名は必須です")
        @Size(max = 100, message = "買い物メモ名は100文字以内で入力してください")
        String name
) {
}
