package com.app.feature.photo.model;

import java.sql.Date;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.app.feature.post.model.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Table;
import lombok.Data;

@Data
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
    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private PostEntity post;

    public PhotoEntity() {
    }

    public PhotoEntity(String url, PostEntity post) {
        this.url = url;
        this.post = post;
    }
}
