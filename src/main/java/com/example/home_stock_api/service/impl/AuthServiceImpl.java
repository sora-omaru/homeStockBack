package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.LoginRequestDto;
import com.example.home_stock_api.dto.response.LoginResult;
import com.example.home_stock_api.dto.response.UserAuthResponseDto;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.security.jwt.JwtTokenProvider;
import com.example.home_stock_api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginResult login(LoginRequestDto request) {
        Optional<UserEntity> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isEmpty()) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        UserEntity user = userOptional.get();

        //パスワードがマッチしていない場合はエラーにする
        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(),
                user.getPasswordHash()
        );
        if (!passwordMatches) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }
        //JWTトークン発行
        String token = jwtTokenProvider.generateToken(user.getPublicId().toString());
        System.out.println("token = " + token);

        UserAuthResponseDto response = new UserAuthResponseDto(user.getPublicId(), user.getDisplayName(), "ログインしました！");
        return new LoginResult(response, token);
    }
}
