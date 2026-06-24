package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LoginResult;
import com.example.home_stock_api.dto.response.MeResponseDto;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;

import java.util.UUID;

public interface AuthService {
    LoginResult login(LoginRequestDto request);

    MeResponseDto getCurrentUser(UUID publicId);
}
