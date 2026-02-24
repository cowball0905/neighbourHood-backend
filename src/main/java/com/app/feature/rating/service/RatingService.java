package com.app.feature.rating.service;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.app.feature.auth.model.User;
import com.app.feature.post.model.PostEntity;
import com.app.feature.rating.model.Rating;
import com.app.feature.rating.repository.RatingRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public RatingService(RatingRepository ratingRepository, SimpMessagingTemplate messagingTemplate) {
        this.ratingRepository = ratingRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void createRating(User receiver, User sender, PostEntity post, float marks, String comment) {
        Rating rating = new Rating(receiver, sender, post, marks, comment);
        ratingRepository.save(rating);
    }

    public List<Rating> getRatings(User user) {
        return ratingRepository.findByUser(user);
    }
}
