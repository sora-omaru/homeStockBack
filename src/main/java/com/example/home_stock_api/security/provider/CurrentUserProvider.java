package com.example.home_stock_api.security.provider;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserProvider {
    //PublicIdの抽出メソッド
    public UUID getPublicId(Authentication authentication) {
        return UUID.fromString(authentication.getName());
    }
}
