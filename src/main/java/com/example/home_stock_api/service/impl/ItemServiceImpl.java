package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.response.ItemResponseDto;
import com.example.home_stock_api.entity.ItemEntity;
import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.repository.ItemRepository;
import com.example.home_stock_api.repository.LocationRepository;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;


    @Override
    public List<ItemResponseDto> getItems(UUID publicId){
        UserEntity user = findUser(publicId);


        return itemRepository.findByUser(user).stream().map(this::toResponse).toList();

    }

    // ItemEntityをItemResponseDtoへ変換する
    private ItemResponseDto toResponse(ItemEntity item) {
        LocationEntity location = item.getLocation();

        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getMinQuantity(),
                item.getCategory(),
                location != null ? location.getId() : null,
                location != null ? location.getName() : null,
                item.getExpirationDate(),
                item.getMemo()
        );
    }

    private UserEntity findUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

}
