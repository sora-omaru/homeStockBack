package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.ShoppingMemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingMemoRepository extends JpaRepository<ShoppingMemoEntity, Long> {
}
