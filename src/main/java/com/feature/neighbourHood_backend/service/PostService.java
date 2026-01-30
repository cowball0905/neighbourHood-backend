package com.feature.neighbourHood_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.feature.neighbourHood_backend.model.entity.PhotoEntity;
import com.feature.neighbourHood_backend.model.entity.PostEntity;
import com.feature.neighbourHood_backend.model.entity.User;
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

    public PostEntity createRequest(String title, String content, int type, UUID userId, int redeemPoints,
            int request_type, int payment_method, boolean is_important) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            PostEntity post = new PostEntity(title, content, type, user.get(), redeemPoints, request_type,
                    payment_method, is_important);
            post = postRepository.save(post);
            return post;
        }
        return null;
    }

    public PostEntity findById(Long id) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            return post.get();
        }
        return null;
    }

    public List<PostEntity> findAll() {
        return postRepository.findAll();
    }

    public int acceptRequest(Long id, UUID uuid) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            Optional<User> user = userRepository.findById(uuid);
            if (user.isPresent()) {
                PostEntity postEntity = post.get();
                postEntity.setAcceptUser(user.get());
                postRepository.save(postEntity);
                return 1;
            } else {
                return 2;
            }
        }
        return 3;
    }

    public void connectPhotos(Long id, List<PhotoEntity> photos) {
        Optional<PostEntity> post = postRepository.findById(id);
        if (post.isPresent()) {
            for (PhotoEntity photo : photos) {
                post.get().addPhoto(photo);
                postRepository.save(post.get());
            }
        }
    }
}
