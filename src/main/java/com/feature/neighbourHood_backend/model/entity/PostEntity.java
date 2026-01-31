package com.feature.neighbourHood_backend.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
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

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createTime;

    // if it is 0, mean it does not have a total duration
    @Column(name = "duration")
    private int duration = 0;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "uuid", nullable = false)
    @JsonIgnoreProperties({ "email", "house" })
    private User user;

    @ManyToOne
    @JoinColumn(name = "accept_user_id", referencedColumnName = "uuid")
    private User acceptUser;

    @OneToMany(mappedBy = "post")
    List<PhotoEntity> postPhotos = new ArrayList<>();

    public PostEntity() {
    }

    public PostEntity(String title, String content,
            int type, User user, int redeemPoints, int request_type, int payment_method, boolean is_important,
            LocalDateTime time, int duration) {
        this.title = title;
        this.content = content;
        this.like_count = 0;
        this.share_count = 0;
        this.type = type;
        this.user = user;
        this.startTime = time;
        this.duration = duration;

        if (type == 0) {
            this.redeemPoints = redeemPoints;
            this.request_type = request_type;
            this.payment_method = payment_method;
        } else {
            this.is_important = is_important;
        }
    }

    public void addPhoto(PhotoEntity photo) {
        postPhotos.add(photo);
    }

    public Long getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAcceptUser(User user) {
        this.acceptUser = user;
    }
}
