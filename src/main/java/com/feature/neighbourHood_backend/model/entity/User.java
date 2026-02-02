package com.feature.neighbourHood_backend.model.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @Column(name = "hkid")
    private String hkid;

    @Column(name = "house")
    private String house;

    public User(String username, String email, String password, Role userRole) {
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles.add(userRole);
        // this.likePosts = new HashSet<>();
    }

    @JsonIgnore
    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<PostEntity> posts = new ArrayList<>();

    public void addPost(PostEntity post) {
        posts.add(post);
        post.setUser(this);
    }

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // @JsonIgnore
    // @ManyToMany(fetch = FetchType.EAGER)
    // @JoinTable(name = "user_likePosts", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "post_id"))
    // private Set<PostEntity> likePosts = new HashSet<>();

    public User() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public List<PostEntity> getPosts() {
        return posts;
    }

    // public void addLike(PostEntity post){
    //     this.likePosts.add(post);
    // }
}
