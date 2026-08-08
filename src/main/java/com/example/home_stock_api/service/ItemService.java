package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemQuantityRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;
import com.example.home_stock_api.entity.ItemCategory;
import com.example.home_stock_api.entity.ItemEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ItemService {
    List<ItemResponseDto> getItems(UUID publicId);

    ItemResponseDto findItem(UUID publicId, Long itemId);

    ItemResponseDto createItem(UUID publicId, ItemCreateRequestDto request);

    void deleteItem(UUID publicId, Long itemId);

    ItemResponseDto updateItem(UUID publicId, Long id, UpdateItemRequestDto request);

    void updateQuantity(UUID publicId, Long itemId, UpdateItemQuantityRequestDto request);

    Map<ItemCategory,Integer> getCategorySummary(UUID publicId);
}
