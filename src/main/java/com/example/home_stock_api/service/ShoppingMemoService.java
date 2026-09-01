package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.ShoppingMemoCreateRequestDto;
import com.example.home_stock_api.dto.response.ShoppingMemoResponseDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ShoppingMemoService {
    List<ShoppingMemoResponseDto> getShoppingMemos(UUID publicId);

    ShoppingMemoResponseDto createShoppingMemo(UUID publicId, @Valid ShoppingMemoCreateRequestDto request);

    void deleteShoppingMemo(UUID publicId, Long shoppingMemoId);
}
