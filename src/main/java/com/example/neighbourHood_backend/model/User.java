package com.example.neighbourHood_backend.model;

public class User {
    String username;
    String password;
    String token;
    String uuid;

    public User(String username, String password, String uuid){
        this.username = username;
        this.password = password;
        this.uuid = uuid;
    }

    public String getName() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public String getUuid(){
        return uuid;
    }

    public void setToken(String token){
        this.token = token;
    }
}
