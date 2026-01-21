package com.feature.neighbourHood_backend.model.DTO;

import java.util.UUID;

public class UserDTO {
    private String username;
    private String email;
    private UUID uuid;

    public UserDTO(String username, UUID uuid, String email) {
        this.username = username;
        this.uuid = uuid;
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
}