package com.example.neighbourHood_backend.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.example.neighbourHood_backend.Entity.UserEntity;
import com.example.neighbourHood_backend.Model.User;
import com.example.neighbourHood_backend.Repository.UserRepository;

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

    public User login(String username, String password){
        for(User user: this.users){
            if(user.getName().equals(username)){
                if(user.getPassword().equals(password)){
                    return user;
                }
                else{
                    return null;
                }
            }
        }
        return null;
    }

    // public User register(String username, String email, String password){
 
    // }

    public static User entityToModel(UserEntity userEntity){
        return new User(userEntity.getName(),userEntity.getPassword(),userEntity.getUUID());
    }
}
