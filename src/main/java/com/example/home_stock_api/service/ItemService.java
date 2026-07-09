package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    List<ItemResponseDto> getItems(UUID publicId);

    ItemResponseDto findItem(UUID publicId, Long itemId);

    ItemResponseDto createItem(UUID publicId, ItemCreateRequestDto request);

    void deleteItem(UUID publicId, Long itemId);

    ItemResponseDto updateItem(UUID publicId, Long id, UpdateItemRequestDto request);
}
