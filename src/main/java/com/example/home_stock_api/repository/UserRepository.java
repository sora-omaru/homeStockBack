package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface UserRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByEmail(String email);

    Optional<UserEntity> findByEmail(String email);
}
