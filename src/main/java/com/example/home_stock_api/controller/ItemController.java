package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemQuantityRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;
import com.example.home_stock_api.entity.ItemCategory;
import com.example.home_stock_api.security.provider.CurrentUserProvider;
import com.example.home_stock_api.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/{id}")
    public ItemResponseDto findItem(Authentication authentication, @PathVariable("id") Long itemId) {
        return itemService.findItem(currentUserProvider.getPublicId(authentication), itemId);
    }

    @PostMapping
    public ItemResponseDto createItem(Authentication authentication, @Valid @RequestBody ItemCreateRequestDto request) {
        return itemService.createItem(currentUserProvider.getPublicId(authentication), request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(Authentication authentication, @PathVariable("id") Long itemId) {
        itemService.deleteItem(currentUserProvider.getPublicId(authentication), itemId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ItemResponseDto updateItem(Authentication authentication, @PathVariable("id") Long itemId, @Valid @RequestBody UpdateItemRequestDto request) {
        return itemService.updateItem(currentUserProvider.getPublicId(authentication), itemId, request);
    }

    @PatchMapping("{id}/quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateQuantity(
            Authentication authentication,
            @PathVariable("id") Long itemId,
            @Valid @RequestBody UpdateItemQuantityRequestDto request
    ) {
        itemService.updateQuantity(
                currentUserProvider.getPublicId(authentication),
                itemId,
                request
        );
    }

    @GetMapping("/summary")
    public Map<ItemCategory, Integer> getCategorySummary(Authentication authentication) {

        return itemService.getCategorySummary(currentUserProvider.getPublicId(authentication));
    }
}
