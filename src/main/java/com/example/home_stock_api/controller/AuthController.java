package com.example.home_stock_api.controller;

import com.example.home_stock_api.config.properties.JwtProperties;
import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LoginResult;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;
import com.example.home_stock_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        LoginResult result = authService.login(request);


        //Cookie設定
        //Cookie名が散らばる可能性があるためJwtPropertiesから参照するようにする
        ResponseCookie cookie = ResponseCookie.from(jwtProperties.getCookieName(), result.token())
                .httpOnly(true)//本番時false
                .secure(false)//本番時"Lax"
                .path("/")
                .maxAge(24 * 60 * 60)
                .sameSite("Lax")
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.response());
    }

}
