package com.example.neighbourHood_backend.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.neighbourHood_backend.Entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    Optional<UserEntity> findByUsername(String username);
}
