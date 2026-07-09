package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.ItemEntity;
import com.example.home_stock_api.entity.LocationEntity;
import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findByUser(UserEntity user);

    @Query("""
                SELECT i
                FROM ItemEntity i
                LEFT JOIN FETCH i.location
                WHERE i.user = :user
            """)
    List<ItemEntity> findByUserWithLocation(@Param("user") UserEntity user);

    @Query("""
            SELECT i
            FROM ItemEntity i
            LEFT JOIN FETCH i.location
            WHERE i.user = :user
            AND i.id = :id
            """)
    Optional<ItemEntity> findByIdAndUserWithLocation(@Param("id") Long id, @Param("user") UserEntity user);

    boolean existsByUserAndName(UserEntity user, String name);

    Optional<ItemEntity> findByIdAndUser(Long id, UserEntity user);


}
