package com.app.feature.photo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.feature.photo.model.PhotoEntity;
import com.app.feature.photo.repository.PhotoRepository;
import com.app.feature.post.model.PostEntity;

@Service
public class PhotoService {
    private final PhotoRepository repository;

    public PhotoService(PhotoRepository repository) {
        this.repository = repository;
    }

    public List<PhotoEntity> savePhoto(List<String> urls, PostEntity post) {
        List<PhotoEntity> photos = new ArrayList<>();
        for (String url : urls) {
            PhotoEntity photo = new PhotoEntity(url, post);
            repository.save(photo);
            photos.add(photo);
        }
        return photos;
    }
}
