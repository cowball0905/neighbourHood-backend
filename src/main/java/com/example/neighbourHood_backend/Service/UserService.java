package com.example.neighbourHood_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.neighbourHood_backend.entity.UserEntity;
import com.example.neighbourHood_backend.model.User;
import com.example.neighbourHood_backend.repository.UserRepository;
import com.example.neighbourHood_backend.request.LoginRequestDTO;
import com.example.neighbourHood_backend.request.LoginResponseDTO;
import com.example.neighbourHood_backend.util.authUtil;
import com.example.neighbourHood_backend.util.jwtUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final List<User> users = new ArrayList<>();

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
        List<UserEntity> usersEntity = userRepository.findAll();
        for(UserEntity userE: usersEntity){
            users.add(entityToModel(userE));
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request){
        String username = request.getUsername();
        String password = request.getPassword();
        for(User user: this.users){
            if(user.getName().equals(username)){
                if(authUtil.matchesPassword(password, user.getPassword())){
                    String token = jwtUtil.createToken(user);
                    LoginResponseDTO response = new LoginResponseDTO();
                    response.setToken(token);
                    response.setUser(username, password);
                    return response;
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }

    public boolean findByUsername(String username){
        if(userRepository.findByUsername(username) != null){
            return true;
        }
        return false;
    }

    // public User register(String username, String email, String password){
 
    // }

    public static User entityToModel(UserEntity userEntity){
        return new User(userEntity.getName(),userEntity.getPassword(),userEntity.getUUID());
    }
}
