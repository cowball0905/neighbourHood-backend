package com.example.neighbourHood_backend.request;

public class UserDTO{
    private String username;
    private String uuid;

    public UserDTO(String username, String uuid) {
        this.username = username;
        this.uuid = uuid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid(){
        return uuid;
    }

    public String getUsername(){
        return username;
    }
}