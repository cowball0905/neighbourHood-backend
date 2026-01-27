package com.feature.neighbourHood_backend.model.DTO;

import java.util.UUID;

public class LoginResponseDTO {
    private String token;
    private UserDTO user;
    private String message;

    public LoginResponseDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public void setUser(String username, UUID uuid, String email) {
        this.user = new UserDTO(username, uuid, email);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}