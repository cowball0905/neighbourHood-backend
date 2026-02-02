package com.feature.neighbourHood_backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

@PreAuthorize("isAuthenticated()")
@RestController
@RequestMapping("/api/post")
public class PostController {
    private final SupabaseStorageService storageService;
    private final PostService postService;
    private final PhotoService photoService;

    public PostController(SupabaseStorageService storageService, PostService postService, PhotoService photoService,
            jwtUtil jwtUtil) {
        this.storageService = storageService;
        this.postService = postService;
        this.photoService = photoService;
    }

    @PostMapping("/create-post")
    public ResponseEntity<ApiResponse> createRequest(@AuthenticationPrincipal CustomUserDetails userDetails,
            @ModelAttribute createPostDTO request) {
        PostEntity post = postService.createRequest(request.getTitle(), request.getContent(), request.getType(),
                userDetails.getUuid(),
                request.getRedeemPoints(), request.getRequestType(), request.getPaymentMethod(),
                request.getIsImportant(), request.getStartTime(), request.getEndTime());

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

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getRequest() {
        List<PostEntity> posts = postService.findAll();
        if (posts != null) {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, posts, "success"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, null, "fail"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostEntity post = postService.findById(id);
        if (post != null) {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, post, "success"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, null, "fail"));
        }
    }

    @PostMapping("/accept-post")
    public ResponseEntity<ApiResponse> acceptPost(@RequestBody Long postID, @RequestBody UUID uuid) {
        int response = postService.acceptRequest(postID, uuid);
        if (response == 1) {
            return ResponseEntity.status(200).body(new ApiResponse<>(true, "success"));
        } else if (response == 2) {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "fail to find corresponding user"));
        } else {
            return ResponseEntity.status(404).body(new ApiResponse<>(false, "fail to find the post"));
        }
    }

    // @PostMapping("/like-post")
    // public ResponseEntity<ApiResponse> likePost(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody Long postID) {
    //     int response = postService.likePost(postID, userDetails.getUuid());
    //     if (response == 1) {
    //         return ResponseEntity.status(200).body(new ApiResponse<>(true, true,"success"));
    //     } else {
    //         return ResponseEntity.status(404).body(new ApiResponse<>(false,false, "fail to find corresponding user or post"));
    //     } 
    // }
}