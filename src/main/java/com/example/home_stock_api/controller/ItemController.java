package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;
import com.example.home_stock_api.security.provider.CurrentUserProvider;
import com.example.home_stock_api.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;
    private final CurrentUserProvider currentUserProvider;

    @GetMapping
    public List<ItemResponseDto> getItems(Authentication authentication) {
        return itemService.getItems(currentUserProvider.getPublicId(authentication));
    }

    @PostMapping
    public ItemResponseDto createItem(Authentication authentication,@Valid @RequestBody ItemCreateRequestDto request) {
        return itemService.createItem(currentUserProvider.getPublicId(authentication), request);
    }
}
