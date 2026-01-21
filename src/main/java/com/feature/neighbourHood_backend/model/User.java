package com.feature.neighbourHood_backend.model;

import java.util.UUID;

public class User {
    String username;
    String email;
    String hkid;
    String password;
    String house;
    String token;
    UUID uuid;

    public User(String username, String email, String password, String hkid, UUID uuid,String house){
        this.username = username;
        this.email = email;
        this.password = password;
        this.hkid = hkid;
        this.uuid = uuid;
        this.house = house;
    }

    public String getName() {
        return username;
    }

    public String getPassword(){
        return password;
    }

    public UUID getUuid(){
        return uuid;
    }

    public void setToken(String token){
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHkid() {
        return hkid;
    }

    public void setHkid(String hkid) {
        this.hkid = hkid;
    }

    public String getToken() {
        return token;
    }

    public String getHouse(){
        return this.house;
    }

    public void setHouse(String house){
        this.house = house;
    }
}
