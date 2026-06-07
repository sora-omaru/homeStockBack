package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.dto.request.RegisterUserRequestDto;
import com.example.home_stock_api.dto.response.RegisterUserResponseDto;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public RegisterUserResponseDto register (RegisterUserRequestDto request) {
       if(!request.getPassword().equals(request.getPasswordConfirm())){
           throw new IllegalArgumentException("パスワードと確認用のパスワードが一致しません！");
       }



        return null;

    }
}
