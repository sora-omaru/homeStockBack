package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationEntity, Long> {
    List<LocationEntity> findByUser(UserEntity user);

    boolean existsByUserAndName(UserEntity user, String name);

    Optional<LocationEntity> findByIdAndUser_PublicId(Long id, UUID publicId);

    boolean existsByUserAndNameAndIdNot(UserEntity user, String name, Long id);
}
