package com.feature.neighbourHood_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.feature.neighbourHood_backend.model.entity.PhotoEntity;
import com.feature.neighbourHood_backend.model.entity.PostEntity;
import com.feature.neighbourHood_backend.repository.PhotoRepository;

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
