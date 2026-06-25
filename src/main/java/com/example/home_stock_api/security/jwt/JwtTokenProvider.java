package com.example.home_stock_api.security.jwt;

import com.example.home_stock_api.config.properties.JwtProperties;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    private final SecretKey key;
//共通Keyで実行できるようにコンストラクタ
    public JwtTokenProvider(JwtProperties jwtProperties){
        this.jwtProperties = jwtProperties;
        if (!StringUtils.hasText(jwtProperties.getSecret())) {
            throw new IllegalArgumentException("jwt.secret must be configured");
        }
        if (jwtProperties.getExpirationMs() <= 0) {
            throw new IllegalArgumentException("jwt.expiration-ms must be greater than 0");
        }
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }
    //JWT認証発行
    public String generateToken(String publicId) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + jwtProperties.getExpirationMs());

//        SecretKey key = Keys.hmacShaKeyFor(
//                jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8)
//        );

        return Jwts.builder().subject(publicId).issuedAt(now).expiration(expiration).signWith(key).compact();
    }

    //読み取り処理
    //署名検証と解析
    public boolean validateToken(String token) {
        try {

            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    //publicIdを抽出(生成時にsubjectに登録)
    public String getPublicIdFromToken(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }
}
