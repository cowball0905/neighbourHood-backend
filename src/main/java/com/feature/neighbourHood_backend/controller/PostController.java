package com.feature.neighbourHood_backend.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.feature.neighbourHood_backend.model.CustomUserDetails;
import com.feature.neighbourHood_backend.model.DTO.ApiResponse;
import com.feature.neighbourHood_backend.model.DTO.createPostDTO;
import com.feature.neighbourHood_backend.model.entity.PhotoEntity;
import com.feature.neighbourHood_backend.model.entity.PostEntity;
import com.feature.neighbourHood_backend.service.PhotoService;
import com.feature.neighbourHood_backend.service.PostService;
import com.feature.neighbourHood_backend.service.SupabaseStorageService;
import com.feature.neighbourHood_backend.util.jwtUtil;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final SupabaseStorageService storageService;
    private final PostService postService;
    private final PhotoService photoService;
    private final jwtUtil jwtUtil;

    public PostController(SupabaseStorageService storageService, PostService postService, PhotoService photoService,
            jwtUtil jwtUtil) {
        this.storageService = storageService;
        this.postService = postService;
        this.photoService = photoService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/create-post")
    public ResponseEntity<ApiResponse> createRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute createPostDTO request) {
        PostEntity post = postService.createRequest(request.getTitle(), request.getContent(), request.getType(),
                userDetails.getUuid(),
                request.getRedeemPoints(), request.getRequestType(), request.getPaymentMethod(),
                request.getIsImportant());

        List<String> urls = new ArrayList<>();
        if (request.getFiles() != null) {
            for (MultipartFile file : request.getFiles()) {
                String url = storageService.uploadFile(file, "request");
                if (url != null)
                    urls.add(url);
            }
        }
        List<PhotoEntity> photos = photoService.savePhoto(urls, post);
        postService.connectPhotos(post.getId(), photos);
        return ResponseEntity.status(200)
                .body(new ApiResponse<>("200", true, post.getId(), "Request created successfully"));
    }
}