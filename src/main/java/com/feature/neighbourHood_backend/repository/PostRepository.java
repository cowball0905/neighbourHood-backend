package com.feature.neighbourHood_backend.repository;

import com.feature.neighbourHood_backend.model.entity.PostEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {

}
