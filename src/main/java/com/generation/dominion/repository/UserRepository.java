package com.generation.dominion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.dominion.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> 
{
    Optional<UserEntity> findByUsername(String username);
    
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String username);
}
