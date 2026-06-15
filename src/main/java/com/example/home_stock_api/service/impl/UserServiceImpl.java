package com.example.home_stock_api.service.impl;


import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.RegisterUserRequestDto;
import com.example.home_stock_api.dto.response.RegisterUserResponseDto;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;//pwハッシュ化

    @Override
    public RegisterUserResponseDto register(RegisterUserRequestDto request) {
        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new BusinessException(ErrorCode.PASSWORD_MISMATCH);
        }
//E-mailが重複していた場合のエラー
        boolean emailExists = userRepository.existsByEmail(request.getEmail());
        if (emailExists) {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        UUID publicId = UUID.randomUUID();//publicId生成


        UserEntity user = new UserEntity();
        user.setEmail(request.getEmail());
        user.setPublicId(publicId);
        user.setPasswordHash(hashedPassword);
        user.setDisplayName(request.getDisplayName());

        UserEntity savedUser = userRepository.save(user);
        String message = "登録完了！";
        //ユーザー登録にあたっての返却値登録
        RegisterUserResponseDto response = new RegisterUserResponseDto(savedUser.getPublicId(), savedUser.getDisplayName(), message);
        return response;

    }
}
