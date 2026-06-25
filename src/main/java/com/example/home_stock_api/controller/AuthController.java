package com.example.home_stock_api.controller;

import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LoginResult;
import com.example.home_stock_api.dto.response.MeResponseDto;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;
import com.example.home_stock_api.security.jwt.JwtCookieFactory;
import com.example.home_stock_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtCookieFactory jwtCookieFactory;

    //JWT認証後の判定用
    @GetMapping("/me")
    public MeResponseDto me(Authentication authentication) {
        UUID publicId = UUID.fromString(authentication.getName());

        return authService.getCurrentUser(publicId);
    }

    //Cookie登録
    @PostMapping("/login")
    public ResponseEntity<UserAuthResponseDto> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        LoginResult result = authService.login(request);

        ResponseCookie cookie = jwtCookieFactory.createLoginCookie(result.token());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result.response());
    }

    //Cookie削除
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie cookie = jwtCookieFactory.createLogoutCookie();

        return ResponseEntity.noContent().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
    }

}
