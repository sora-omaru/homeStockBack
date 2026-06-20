package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.RegisterUserRequestDto;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;
import com.example.home_stock_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RegisterUserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserAuthResponseDto> register(@Valid @RequestBody RegisterUserRequestDto request){
        UserAuthResponseDto response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
