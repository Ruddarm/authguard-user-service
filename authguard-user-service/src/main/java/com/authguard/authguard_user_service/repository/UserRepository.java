package com.authguard.authguard_user_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.authguard.authguard_user_service.model.entity.UserEntity;

public interface   UserRepository extends  JpaRepository<UserEntity, UUID> {
    
    public boolean existsByEmail(String email);
    public Optional<UserEntity> findByEmail(String email);
}
