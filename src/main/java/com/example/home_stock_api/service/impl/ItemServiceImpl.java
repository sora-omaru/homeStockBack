package com.example.home_stock_api.service.impl;

import com.example.home_stock_api.common.error.BusinessException;
import com.example.home_stock_api.common.error.ErrorCode;
import com.example.home_stock_api.dto.request.ItemCreateRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemPercentageDto;
import com.example.home_stock_api.dto.request.UpdateItemQuantityRequestDto;
import com.example.home_stock_api.dto.request.UpdateItemRequestDto;
import com.example.home_stock_api.dto.response.ItemResponseDto;
import com.example.home_stock_api.entity.ItemCategory;
import com.example.home_stock_api.entity.ItemEntity;
import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import com.example.home_stock_api.entity.enums.StockType;
import com.example.home_stock_api.repository.ItemRepository;
import com.example.home_stock_api.repository.LocationRepository;
import com.example.home_stock_api.repository.UserRepository;
import com.example.home_stock_api.service.ItemNameNormalizer;
import com.example.home_stock_api.service.ItemService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final ItemNameNormalizer itemNameNormalizer;

    @Override
    public List<ItemResponseDto> getItems(UUID publicId, String keyword) {
        UserEntity user = findUser(publicId);

        //すべてを返す
        if (keyword == null || keyword.isBlank()) {
            return itemRepository.findByUserWithLocation(user).stream().map(this::toResponse).toList();
        }
        //検索用のItemを返す
        String normalizedKeyword = itemNameNormalizer.normalize(keyword);

        return itemRepository.findByUser_PublicIdAndNormalizedNameContaining(publicId, normalizedKeyword).stream().map(this::toResponse).toList();

    }

    @Override
    public ItemResponseDto findItem(UUID publicId, Long itemId) {
        UserEntity user = findUser(publicId);
        ItemEntity item = itemRepository.findByIdAndUserWithLocation(itemId, user).orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
        return toResponse(item);
    }

    @Override
    public ItemResponseDto createItem(UUID publicId, ItemCreateRequestDto request) {
        UserEntity user = findUser(publicId);

        if (itemRepository.existsByUserAndName(user, request.name())) {
            throw new BusinessException(ErrorCode.ITEM_ALREADY_EXISTS);
        }

        //LocationIdがNullの場合はそのままにする
        Long locationId = request.locationId();
        LocationEntity location = null;
        if (locationId != null) {
            location = findLocation(user, locationId);
        }

        ItemEntity item = new ItemEntity();
        item.setUser(user);
        item.setLocation(location);
        item.setName(request.name());
        item.setNormalizedName(itemNameNormalizer.normalize(request.name()));
        item.setStockType(request.stockType());

//        stockTypeをみて個数か割合かを判断する
        if (request.stockType() == StockType.QUANTITY) {
            item.setQuantity(request.quantity());
            item.setMinQuantity(request.minQuantity());

            item.setStockPercentage(null);
            item.setMinPercentage(null);

        } else if (request.stockType() == StockType.PERCENTAGE) {
            item.setQuantity(null);
            item.setMinQuantity(null);

            item.setStockPercentage(request.stockPercentage());
            item.setMinPercentage(request.minPercentage());
        }
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
    public ItemResponseDto updateItem(UUID publicId, Long id, UpdateItemRequestDto request) {
        UserEntity user = findUser(publicId);
        ItemEntity item = findItem(user, id);


        //nameが更新された場合、既存のItemたちと重複チェックをする
        if (!item.getName().equals(request.name())) {
            if (itemRepository.existsByUserAndName(user, request.name())) {
                throw new BusinessException(ErrorCode.ITEM_ALREADY_EXISTS);
            }
        }
        //locationIdがNullの場合はNullで更新する
        Long locationId = request.locationId();
        LocationEntity location = null;
        if (locationId != null) {
            location = findLocation(user, locationId);
        }

        item.setName(request.name());
        item.setNormalizedName(itemNameNormalizer.normalize(request.name()));
        item.setStockType(request.stockType());

        //stockTypeをみて個数か割合かを判断する
        if (request.stockType() == StockType.QUANTITY) {
            item.setQuantity(request.quantity());
            item.setMinQuantity(request.minQuantity());
            item.setStockPercentage(null);
            item.setMinPercentage(null);
        } else {
            item.setQuantity(null);
            item.setMinQuantity(null);
            item.setStockPercentage(request.stockPercentage());
            item.setMinPercentage(request.minPercentage());
        }

        item.setCategory(request.category());
        item.setLocation(location);
        item.setMemo(request.memo());
        item.setExpirationDate(request.expirationDate());

        ItemEntity savedItem = itemRepository.save(item);

        return toResponse(savedItem);

    }

    @Override
    @Transactional
    public void updateQuantity(UUID publicId, Long itemId, UpdateItemQuantityRequestDto request) {
        ItemEntity item = itemRepository.findByIdAndUser_publicId(itemId, publicId).orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
//StockTypeが個数を指定していないときは使用できないようにする。
        if (item.getStockType() != StockType.QUANTITY) {
            throw new BusinessException(ErrorCode.INVALID_STOCK_TYPE);
        }
        item.setQuantity(request.quantity());
    }

    @Override
    @Transactional
    public void updatePercentage(UUID publicId, Long itemId, UpdateItemPercentageDto request) {
        ItemEntity item = itemRepository.findByIdAndUser_publicId(itemId, publicId).orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));

        if (item.getStockType() != StockType.PERCENTAGE) {
            throw new BusinessException(ErrorCode.INVALID_STOCK_TYPE);
        }
        item.setStockPercentage(request.stockPercentage());
    }

    @Override
    public Map<ItemCategory, Integer> getCategorySummary(UUID publicId) {
        UserEntity user = findUser(publicId);
        List<ItemEntity> items = itemRepository.findByUserWithLocation(user);

        return items.stream().collect(Collectors.groupingBy(ItemEntity::getCategory, Collectors.summingInt(item -> 1)));

//        Map<ItemCategory, Integer> map = new HashMap<>();

//        for (ItemEntity item : items) {
//            ItemCategory category = item.getCategory();
//
//            Integer count = map.get(category);
//
//            if (count == null) {
//                map.put(category, 1);
//
//            } else {
//                map.put(category, count + 1);
//            }
//        }
        //        System.out.println(map);
//        return map;
    }


    // ItemEntityをItemResponseDtoへ変換する
    private ItemResponseDto toResponse(ItemEntity item) {
        LocationEntity location = item.getLocation();

        return new ItemResponseDto(
                item.getId(),
                item.getName(),
                item.getQuantity(),
                item.getMinQuantity(),
                item.getStockType(),
                item.getStockPercentage(),
                item.getMinPercentage(),
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
        return itemRepository.findByIdAndUser(itemId, user).orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private UserEntity findUser(UUID publicId) {
        return userRepository.findByPublicId(publicId).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
    }

//    //名前を文字列を変換する
//    private String normalizeName(String name) {
//        String normalized = Normalizer.normalize(
//                name,
//                Normalizer.Form.NFKC
//        ).toLowerCase();
//
//        StringBuilder result = new StringBuilder();
//
//        for (char c : normalized.toCharArray()) {
//            if (c >= 'ァ' && c <= 'ヶ') {
//                c = (char) (c - 'ァ' + 'ぁ');
//            }
//
//            result.append(c);
//        }
//
//        return result.toString();
//    }
}
