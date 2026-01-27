package com.feature.neighbourHood_backend.model.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "hkid")
    private String hkid;

    @Column(name = "house")
    private String house;

    public UserEntity(String username, String email, String password) {
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {
    }

    public String getName() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.username = name;
    }

    public String getEmail() {
        return email;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHKID() {
        return this.hkid;
    }
}
