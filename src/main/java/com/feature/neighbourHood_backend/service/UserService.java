package com.feature.neighbourHood_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.feature.neighbourHood_backend.model.User;
import com.feature.neighbourHood_backend.model.DTO.LoginRequestDTO;
import com.feature.neighbourHood_backend.model.DTO.LoginResponseDTO;
import com.feature.neighbourHood_backend.model.DTO.RegisterRequestDTO;
import com.feature.neighbourHood_backend.model.entity.UserEntity;
import com.feature.neighbourHood_backend.repository.UserRepository;
import com.feature.neighbourHood_backend.util.authUtil;
import com.feature.neighbourHood_backend.util.jwtUtil;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final List<User> users = new ArrayList<>();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        List<UserEntity> usersEntity = userRepository.findAll();
        for (UserEntity userE : usersEntity) {
            users.add(entityToModel(userE));
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String username = request.getUsername();
        String password = request.getPassword();
        for (User user : this.users) {
            if (user.getName().equals(username)) {
                if (authUtil.matchesPassword(password, user.getPassword())) {
                    String token = jwtUtil.createToken(user);
                    LoginResponseDTO response = new LoginResponseDTO();
                    response.setToken(token);
                    response.setUser(username, user.getUuid(), user.getEmail());
                    return response;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    public String register(RegisterRequestDTO request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();

        for (User user : this.users) {
            if (user.getEmail().equals(email)) {
                return "email repeated";
            }
        }
        String passwordHash = authUtil.encryptPassword(password);
        UserEntity userE = new UserEntity(username, email, passwordHash);
        userRepository.save(userE);
        return "Succeed";
    }

    public boolean findByUsername(String username) {
        if (userRepository.findByUsername(username) != null) {
            return true;
        }
        return false;
    }

    public static User entityToModel(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getHKID(),
                userEntity.getUUID(), null);
    }
}
