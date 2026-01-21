package com.feature.neighbourHood_backend.model.DTO;

public class LoginResponseDTO{
    private String token;
    private UserDTO user;

    public void setToken(String token){
        this.token = token;
    }

    public void setUser(String username, String uuid){
        this.user = new UserDTO(username, uuid);
    }

    public String getToken(){
        return token;
    }

    public UserDTO getUser(){
        return user;
    }
}