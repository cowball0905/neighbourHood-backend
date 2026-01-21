package com.feature.neighbourHood_backend.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.feature.neighbourHood_backend.model.DTO.LoginRequestDTO;
import com.feature.neighbourHood_backend.model.DTO.LoginResponseDTO;
import com.feature.neighbourHood_backend.model.DTO.RegisterRequestDTO;
import com.feature.neighbourHood_backend.service.UserService;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = userService.login(request);

        if (response != null) {
            return ResponseEntity.ok(response);
        } else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String,String>>login(@RequestBody RegisterRequestDTO request) {
        String responseString = userService.register(request);
        Map<String,String> response = new HashMap<>();
        response.put("message", responseString);

        if (response.get("message").equals("Succeed")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body(response);
        }
    }
}
