package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.ShoppingMemoCreateRequestDto;
import com.example.home_stock_api.dto.response.ShoppingMemoResponseDto;
import com.example.home_stock_api.security.provider.CurrentUserProvider;
import com.example.home_stock_api.service.ShoppingMemoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shopping-memos")
public class ShoppingMemoController {
    private final ShoppingMemoService shoppingMemoService;
    private final CurrentUserProvider currentUserProvider;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingMemoResponseDto createShoppingMemo(
            Authentication authentication,
            @Valid @RequestBody ShoppingMemoCreateRequestDto request
    ) {
        return shoppingMemoService.createShoppingMemo(
                currentUserProvider.getPublicId(authentication),
                request
        );
    }
}
