package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findByUser(UserEntity user);
}
