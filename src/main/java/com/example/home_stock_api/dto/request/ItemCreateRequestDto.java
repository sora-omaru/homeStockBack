package com.example.home_stock_api.dto.request;

import com.example.home_stock_api.entity.ItemCategory;
import com.example.home_stock_api.entity.enums.StockType;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ItemCreateRequestDto(

        @NotBlank(message = "Item名は必須です")
        @Size(max = 100, message = "100文字以内で入力してください")
        String name,


        @PositiveOrZero(message = "数量は0以上で入力してください")
        Integer quantity,


        @PositiveOrZero(message = "最低在庫数は0以上で入力してください")
        Integer minQuantity,

        @NotNull(message = "在庫管理方法は必須です")
        StockType stockType,

        @PositiveOrZero(message = "割合は0以上で入力してください")
        @Max(value = 200, message = "割合は200以下で入力してください")
        Integer stockPercentage,

        @PositiveOrZero(message = "最低在庫割合は0以上で入力してください")
        @Max(value = 200, message = "最低在庫割合は200以下で入力してください")
        Integer minPercentage,

        Long locationId,

        @NotNull(message = "カテゴリは必須です")
        ItemCategory category,

        @Size(max = 1000, message = "メモは1000文字以内で入力してください")
        String memo,

        LocalDate expirationDate

) {
    @AssertTrue(message = "在庫管理方法に対応する在庫値を入力してください")
    public boolean isStockValueValid() {
        if (stockType == null) {
            return true;
        }

        return switch (stockType) {
            case QUANTITY -> quantity != null
                    && stockPercentage == null
                    && minPercentage == null;
            case PERCENTAGE -> stockPercentage != null
                    && quantity == null
                    && minQuantity == null;
        };
    }
}
