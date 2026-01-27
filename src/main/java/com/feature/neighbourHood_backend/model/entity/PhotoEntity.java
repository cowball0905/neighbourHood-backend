package com.feature.neighbourHood_backend.model.entity;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;

@Table(name = "photos")
@Entity
public class PhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "url")
    private String url;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "uploaded_at", insertable = false, updatable = false)
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public PhotoEntity() {
    }

    public PhotoEntity(String url, PostEntity post) {
        this.url = url;
        this.post = post;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
