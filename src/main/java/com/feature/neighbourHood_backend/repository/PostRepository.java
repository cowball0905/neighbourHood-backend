package com.feature.neighbourHood_backend.repository;

import java.util.Optional;

import com.feature.neighbourHood_backend.model.entity.PostEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    Optional<PostEntity> findById(Long id);
}
