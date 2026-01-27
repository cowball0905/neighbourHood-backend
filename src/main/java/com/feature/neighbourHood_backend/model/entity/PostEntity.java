package com.feature.neighbourHood_backend.model.entity;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import com.feature.neighbourHood_backend.model.entity.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class PostEntity {
    public enum PostEnum {
        REQUEST,
        ANNOUNCEMENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "like_count")
    private int like_count;

    @Column(name = "share_count")
    private int share_count;

    @Column(name = "points_to_redeem")
    private int redeemPoints;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "type", nullable = false)
    private PostEnum type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    List<PhotoEntity> postPhotos = new ArrayList<>();

    public PostEntity() {
    }

    public PostEntity(String title, String content,
            PostEnum type, UserEntity user2, int redeemPoints) {
        this.title = title;
        this.content = content;
        this.like_count = 0;
        this.share_count = 0;
        this.type = type;
        this.user = user2;

        if (type.ordinal() == 0) {
            this.redeemPoints = redeemPoints;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public int getShare_count() {
        return share_count;
    }

    public void setShare_count(int share_count) {
        this.share_count = share_count;
    }

    public int getRedeemPoints() {
        return redeemPoints;
    }

    public void setRedeemPoints(int redeemPoints) {
        this.redeemPoints = redeemPoints;
    }

    public PostEnum getType() {
        return type;
    }

    public void addPhoto(PhotoEntity photo) {
        postPhotos.add(photo);
    }

    public void setType(PostEnum type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
