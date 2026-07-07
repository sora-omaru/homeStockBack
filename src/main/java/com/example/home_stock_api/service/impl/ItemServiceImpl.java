package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemRequestDto;
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
    private final LocationRepository locationRepository;

    @Override
    public List<ItemResponseDto> getItems(UUID publicId) {
        UserEntity user = findUser(publicId);


        return itemRepository.findByUserWithLocation(user).stream().map(this::toResponse).toList();

    }

    @Override
    public ItemResponseDto createItem(UUID publicId, ItemCreateRequestDto request) {
        UserEntity user = findUser(publicId);

        if (itemRepository.existsByUserAndName(user, request.name())) {
            throw new BusinessException(ErrorCode.ITEM_ALREADY_EXISTS);
        }
        Long locationId = request.locationId();
        LocationEntity location = null;
        if (locationId != null) {
            location = findLocation(user, locationId);
        }

        ItemEntity item = new ItemEntity();
        item.setUser(user);
        item.setLocation(location);
        item.setName(request.name());
        item.setQuantity(request.quantity());
        item.setMinQuantity(request.minQuantity());
        item.setCategory(request.category());
        item.setMemo(request.memo());
        item.setExpirationDate(request.expirationDate());

        ItemEntity savedItem = itemRepository.save(item);


        return toResponse(savedItem);
    }

    @Override
    public void deleteItem(UUID publicId, Long itemId) {
        UserEntity user = findUser(publicId);
        ItemEntity item = findItem(user, itemId);

        itemRepository.delete(item);
    }

    @Override
    public ItemResponseDto updateItem(UUID publicId, UpdateItemRequestDto request , Long id){
        return null;

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

    private LocationEntity findLocation(UserEntity user, Long locationId) {
        return locationRepository.findByIdAndUser(locationId, user).orElseThrow(() -> new BusinessException(ErrorCode.LOCATION_NOT_FOUND));
    }

    private ItemEntity findItem(UserEntity user, Long itemId) {
        return itemRepository
                .findByIdAndUser(itemId, user)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private UserEntity findUser(UUID publicId) {
        return userRepository.findByPublicId(publicId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

}
