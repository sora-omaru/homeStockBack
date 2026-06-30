package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public record UpdateLocationRequestDto(
        @NotBlank(message = "場所名は必須です")
        @Size(max = 100, message = "場所名は100文字以内です")
        String name
) {}
