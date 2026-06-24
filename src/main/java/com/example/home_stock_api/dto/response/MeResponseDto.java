package com.example.home_stock_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class MeResponseDto {
    private UUID publicId;
    private String displayName;
}
