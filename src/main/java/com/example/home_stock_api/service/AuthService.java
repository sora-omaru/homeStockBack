package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.AuthResult;
import com.example.home_stock_api.dto.response.MeResponseDto;

import java.util.UUID;

public interface AuthService {
    AuthResult login(LoginRequestDto request);

    MeResponseDto getCurrentUser(UUID publicId);
}
