package com.feature.neighbourHood_backend.service;

import java.util.Optional;
import java.util.UUID;

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

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        String email = request.getEmail();
        String password = request.getPassword();
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            if (authUtil.matchesPassword(password, entityToModel(user.get()).getPassword())) {
                String token = jwtUtil.createToken(entityToModel(user.get()));
                LoginResponseDTO response = new LoginResponseDTO();
                response.setToken(token);
                response.setUser(entityToModel(user.get()).getName(), entityToModel(user.get()).getUuid(),
                        entityToModel(user.get()).getEmail());
                response.setMessage("Login Successfully!");
                return response;
            } else {
                LoginResponseDTO response = new LoginResponseDTO();
                response.setMessage("Invalid password");
                return response;
            }
        }
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Invalid email");
        return response;
    }

    public String register(RegisterRequestDTO request) {
        String username = request.getUsername();
        String email = request.getEmail();
        String password = request.getPassword();

        if (userRepository.findByEmail(email).get().getEmail().equals(email)) {
            return "email repeated";
        }
        String passwordHash = authUtil.encryptPassword(password);
        UserEntity userE = new UserEntity(username, email, passwordHash);
        userRepository.save(userE);
        return "Succeed";
    }

    public boolean findByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }

    public static User entityToModel(UserEntity userEntity) {
        return new User(userEntity.getName(), userEntity.getEmail(), userEntity.getPassword(), userEntity.getHKID(),
                userEntity.getUUID(), null);
    }

    public boolean findById(UUID uuid) {
        if (userRepository.findById(uuid).isPresent()) {
            return true;
        }
        return false;
    }
}
