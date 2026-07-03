package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.ItemEntity;
import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity,Long> {
    List<ItemEntity> findByUser(UserEntity user);
}
