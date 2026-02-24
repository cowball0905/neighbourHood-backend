package com.app.feature.rating.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ApiResponse;
import com.app.feature.auth.model.CustomUserDetails;
import com.app.feature.auth.model.User;
import com.app.feature.auth.service.UserService;
import com.app.feature.post.model.PostEntity;
import com.app.feature.post.service.PostService;
import com.app.feature.rating.dto.createRatingDTO;
import com.app.feature.rating.model.Rating;
import com.app.feature.rating.service.RatingService;

@RestController
@RequestMapping("/api/rating")
@PreAuthorize("isAuthenticated()")
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;
    private final PostService postService;

    public RatingController(RatingService ratingService, UserService userService, PostService postService) {
        this.ratingService = ratingService;
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ApiResponse<List<Rating>>> getRating(@PathVariable("id") UUID id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        User user = userService.findById(id);
        List<Rating> ratings = ratingService.getRatings(user);
        return ResponseEntity.status(200).body(new ApiResponse<List<Rating>>(true, ratings, "success"));
    }

    @PostMapping("/post")
    public ResponseEntity<ApiResponse<Boolean>> createRating(
            @AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody createRatingDTO dto) {
        User sender = userService.findById(dto.getSenderUUID());
        User receiver = userService.findById(dto.getRecipientUUID());
        PostEntity post = postService.findById(dto.getPostID());
        if (sender != null && receiver != null) {
            ratingService.createRating(receiver, sender, post, dto.getMarks(), dto.getComment());
            return ResponseEntity.status(200).body(new ApiResponse<>(true, true, "succeed"));
        }
        return ResponseEntity.status(400).body(new ApiResponse<>(false, false, "fail"));
    }
}
