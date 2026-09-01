package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.ShoppingMemoCreateRequestDto;
import com.example.home_stock_api.dto.response.ShoppingMemoResponseDto;
import jakarta.validation.Valid;

import java.util.UUID;

public interface ShoppingMemoService {
    ShoppingMemoResponseDto createShoppingMemo(UUID publicId, @Valid ShoppingMemoCreateRequestDto request);
}
