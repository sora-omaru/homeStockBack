package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.ShoppingMemoCreateRequestDto;
import com.example.home_stock_api.dto.response.ShoppingMemoResponseDto;
import com.example.home_stock_api.entity.ShoppingMemoEntity;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.repository.ShoppingMemoRepository;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.ShoppingMemoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShoppingMemoServiceImpl implements ShoppingMemoService {
    private final UserRepository userRepository;
    private final ShoppingMemoRepository shoppingMemoRepository;

    @Override
    @Transactional
    public List<ShoppingMemoResponseDto> getShoppingMemos(UUID publicId) {
        UserEntity user = findUser(publicId);

        return shoppingMemoRepository.findByUserOrderByCreatedAtDesc(user).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ShoppingMemoResponseDto createShoppingMemo(UUID publicId, ShoppingMemoCreateRequestDto request) {
        UserEntity user = findUser(publicId);

        ShoppingMemoEntity shoppingMemo = new ShoppingMemoEntity();
        shoppingMemo.setName(request.name());
        shoppingMemo.setUser(user);

        ShoppingMemoEntity savedMemo = shoppingMemoRepository.save(shoppingMemo);

        return toResponse(savedMemo);
    }

    @Override
    @Transactional
    public void deleteShoppingMemo(UUID publicId, Long shoppingMemoId) {
        UserEntity user = findUser(publicId);
        ShoppingMemoEntity shoppingMemo = shoppingMemoRepository
                .findByIdAndUser(shoppingMemoId, user)
                .orElseThrow(() -> new BusinessException(ErrorCode.SHOPPING_MEMO_NOT_FOUND));

        shoppingMemoRepository.delete(shoppingMemo);
    }

    private UserEntity findUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

    private ShoppingMemoResponseDto toResponse(ShoppingMemoEntity shoppingMemo) {
        return new ShoppingMemoResponseDto(
                shoppingMemo.getId(),
                shoppingMemo.getName(),
                shoppingMemo.getCreatedAt()
        );
    }
}
