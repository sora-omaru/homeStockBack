package com.example.home_stock_api.batch;

import com.example.home_stock_api.entity.ItemEntity;
import com.example.home_stock_api.repository.ItemRepository;
import com.example.home_stock_api.service.ItemNameNormalizer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
//すでに登録してあるItem名を正規化するバッチ処理
public class ItemNormalizedNameBackfill  {
    private final ItemRepository itemRepository;
    private final ItemNameNormalizer itemNameNormalizer;

    @Transactional
    public void execute() {
        List<ItemEntity> items = itemRepository.findByNormalizedNameIsNull();

        for (ItemEntity item : items) {
            item.setNormalizedName(itemNameNormalizer.normalize(item.getName()));
        }
    }
}
