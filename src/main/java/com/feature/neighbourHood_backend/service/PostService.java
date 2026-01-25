package com.feature.neighbourHood_backend.service;

import com.feature.neighbourHood_backend.repository.PostRepository;

public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }
}
