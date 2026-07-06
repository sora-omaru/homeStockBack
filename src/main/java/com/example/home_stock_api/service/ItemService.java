package com.example.home_stock_api.service;

import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;

import java.util.List;
import java.util.UUID;

public interface ItemService {
    List<ItemResponseDto> getItems(UUID publicId);

   ItemResponseDto createItem(UUID publicId, ItemCreateRequestDto requestDto);
}
