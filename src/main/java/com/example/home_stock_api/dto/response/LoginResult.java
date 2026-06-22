package com.example.home_stock_api.dto.response;

public record LoginResult(UserAuthResponseDto response,
                          String token) {
}
