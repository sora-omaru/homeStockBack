package com.example.home_stock_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserResponseDto {
    private UUID publicId;

    private String displayName;

    private String message;
}
