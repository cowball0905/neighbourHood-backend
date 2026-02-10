package com.app.feature.messaging.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.app.feature.auth.model.User;
import com.app.feature.post.model.PostEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Table(name = "conversations")
@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    @JsonIgnoreProperties({ "email", "house" })
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    @JsonIgnoreProperties({ "email", "house" })
    private User user2;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @CreationTimestamp
    private LocalDateTime createTime;

    @OneToMany
    private List<Message> messages = new ArrayList<>();

    public Conversation() {
    }

    public Conversation(User user1, User user2, PostEntity post) {
        this.user1 = user1;
        this.user2 = user2;
        this.post = post;
    }

    public Long getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public User getUser2() {
        return user2;
    }

    public PostEntity getPost() {
        return post;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public void addMessage(Message msg) {
        this.messages.add(msg);
    }
}
