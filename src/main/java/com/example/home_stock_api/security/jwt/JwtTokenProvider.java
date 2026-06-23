package com.example.home_stock_api.security.jwt;

import com.example.home_stock_api.config.properties.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey key;

    //JWT認証発行
    public String generateToken(String publicId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpirationMs());
//
        SecretKey key = Keys.hmacShaKeyFor(
                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
        );

        return Jwts.builder().subject(publicId).issuedAt(now).expiration(expiration).signWith(key).compact();
    }

    //読み取り処理
    public boolean validationToken(String token){
        try{

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        }catch (){}
    }
}
