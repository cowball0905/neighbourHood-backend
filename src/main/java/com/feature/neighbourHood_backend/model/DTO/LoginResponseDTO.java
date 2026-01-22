package com.feature.neighbourHood_backend.model.DTO;

import java.util.UUID;

public class LoginResponseDTO {
    private String token;
    private UserDTO user;
    private String message;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUser(String username, UUID uuid, String email) {
        this.user = new UserDTO(username, uuid, email);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public UserDTO getUser() {
        return user;
    }
}