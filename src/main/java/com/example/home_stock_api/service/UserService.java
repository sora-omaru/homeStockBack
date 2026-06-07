package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.RegisterUserRequestDto;
import com.example.home_stock_api.dto.response.RegisterUserResponseDto;

public interface UserService {
RegisterUserResponseDto register(RegisterUserRequestDto request);


}
