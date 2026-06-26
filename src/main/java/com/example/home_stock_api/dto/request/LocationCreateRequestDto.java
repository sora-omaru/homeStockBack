package com.example.home_stock_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationCreateRequestDto {
    @NotBlank(message = "場所は必須です")
    @Size(max = 100, message = "100文字以内で入力してください")
    private String name;
}
