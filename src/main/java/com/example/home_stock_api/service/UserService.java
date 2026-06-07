package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.RegisterUserRequestDto;
import com.example.home_stock_api.dto.response.RegisterUserResponseDto;
import com.example.home_stock_api.entity.UserEntity;

import java.util.UUID;

public interface UserService {
RegisterUserResponseDto register(RegisterUserRequestDto request);


}
