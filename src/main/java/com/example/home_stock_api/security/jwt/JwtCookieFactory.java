package com.example.home_stock_api.security.jwt;

import com.example.home_stock_api.config.properties.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtCookieFactory {
    private final JwtProperties jwtProperties;

    public ResponseCookie createLoginCookie(String token) {
        return ResponseCookie.from(jwtProperties.getCookieName(), token)
                .httpOnly(true)
                .secure(jwtProperties.isCookieSecure())
                .path("/")
                .maxAge(jwtProperties.getExpirationMs() / 1000)
                .sameSite(jwtProperties.getCookieSameSite())
                .build();
    }

    public ResponseCookie createLogoutCookie() {
        return ResponseCookie.from(jwtProperties.getCookieName(), "")
                .httpOnly(true)
                .secure(jwtProperties.isCookieSecure())
                .path("/")
                .maxAge(0)
                .sameSite(jwtProperties.getCookieSameSite())
                .build();
    }
}
