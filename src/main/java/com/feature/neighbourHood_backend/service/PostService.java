package com.feature.neighbourHood_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.feature.neighbourHood_backend.model.entity.PhotoEntity;
import com.feature.neighbourHood_backend.model.entity.PostEntity;
import com.feature.neighbourHood_backend.model.entity.PostEntity.PostEnum;
import com.feature.neighbourHood_backend.model.entity.UserEntity;
import com.feature.neighbourHood_backend.repository.PostRepository;
import com.feature.neighbourHood_backend.repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public PostEntity createRequest(String title, String content, UUID userId, int redeemPoints) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            PostEntity post = new PostEntity(title, content, PostEnum.REQUEST, user.get(), redeemPoints);
            post = postRepository.save(post);
            return post;
        }
        return null;
    }

    public Long createAnnouncement(String title, String content, UUID userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if (user.isPresent()) {
            PostEntity post = new PostEntity(title, content, PostEnum.ANNOUNCEMENT, user.get(), 0);
            postRepository.save(post);
            return post.getId();
        }
        return null;
    }

    public Boolean connectPhotos(Long id, List<PhotoEntity> photos) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            for (PhotoEntity photo : photos) {
                post.get().addPhoto(photo);
                postRepository.save(post.get());
            }
            return true;
        }
        return false;
    }
}
