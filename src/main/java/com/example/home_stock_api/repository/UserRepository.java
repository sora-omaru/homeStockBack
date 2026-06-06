package com.example.home_stock_api.repository;

import com.example.home_stock_api.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface UserRepository extends JpaRepository<UserEntity,Long> {

    boolean existsByEmail(String email);
}
