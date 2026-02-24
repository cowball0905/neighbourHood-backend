package com.app.feature.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.feature.auth.model.User;
import com.app.feature.rating.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT c FROM Rating c WHERE (c.receiver = :user)")
    List<Rating> findByUser(@Param("user") User user);
}