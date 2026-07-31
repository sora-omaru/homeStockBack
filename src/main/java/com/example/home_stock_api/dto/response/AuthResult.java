package com.example.home_stock_api.dto.response;

public record AuthResult(UserAuthResponseDto response,
                         String token) {
}
