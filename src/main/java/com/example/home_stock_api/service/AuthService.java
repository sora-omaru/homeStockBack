package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LoginResult;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;

public interface AuthService {
    LoginResult login(LoginRequestDto request);

}
