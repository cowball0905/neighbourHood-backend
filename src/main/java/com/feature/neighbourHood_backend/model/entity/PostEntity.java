package com.feature.neighbourHood_backend.model.entity;

import java.util.List;
import java.util.ArrayList;

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

    @Column(name = "payment_method")
    private int payment_method;

    @Column(name = "request_type")
    private int request_type;

    @Column(name = "is_important")
    private Boolean is_important;

    @Column(name = "points_to_redeem")
    private int redeemPoints;

    @Column(name = "type")
    private int type;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "post")
    List<PhotoEntity> postPhotos = new ArrayList<>();

    public PostEntity() {
    }

    public PostEntity(String title, String content,
            int type, UserEntity user, int redeemPoints, int request_type, int payment_method, boolean is_important) {
        this.title = title;
        this.content = content;
        this.like_count = 0;
        this.share_count = 0;
        this.type = type;
        this.user = user;

        if (type == 0) {
            this.redeemPoints = redeemPoints;
            this.request_type = request_type;
            this.payment_method = payment_method;
        } else {
            this.is_important = is_important;
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

    public int getType() {
        return type;
    }

    public void addPhoto(PhotoEntity photo) {
        postPhotos.add(photo);
    }

    public void setType(int type) {
        this.type = type;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
