package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateItemPercentageDto(
        @NotNull
        @Min(0)
        @Max(200)
        Integer stockPercentage
) {
}
