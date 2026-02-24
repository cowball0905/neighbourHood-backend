package com.app.feature.rating.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.app.feature.auth.model.User;
import com.app.feature.post.model.PostEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "Rating")
@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marks")
    private float marks;

    @Column(name = "comment")
    private String comment;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @Column(name = "uploaded_at", insertable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createTime;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    public Rating() {
    }

    public Rating(User receiver, User sender, PostEntity post, float marks, String comment) {
        this.receiver = receiver;
        this.sender = sender;
        this.post = post;
        this.marks = marks;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMarks() {
        return marks;
    }

    public void setMarks(float marks) {
        this.marks = marks;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }
}
