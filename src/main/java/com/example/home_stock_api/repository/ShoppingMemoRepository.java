package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.ShoppingMemoEntity;
import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShoppingMemoRepository extends JpaRepository<ShoppingMemoEntity, Long> {
    List<ShoppingMemoEntity> findByUserOrderByCreatedAtDesc(UserEntity user);

    Optional<ShoppingMemoEntity> findByIdAndUser(Long id, UserEntity user);
}
