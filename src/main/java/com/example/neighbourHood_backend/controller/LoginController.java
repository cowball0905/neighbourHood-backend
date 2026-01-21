package com.example.neighbourHood_backend.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.neighbourHood_backend.Model.User;
import com.example.neighbourHood_backend.Repository.UserRepository;
import com.example.neighbourHood_backend.Service.UserService;
import com.example.neighbourHood_backend.request.LoginRequestDTO;
import com.example.neighbourHood_backend.util.jwtUtil;

@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO request) {
        String username = request.get("username");
        String password = request.get("password");

        User user = userService.login(username, password);

        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            String token = jwtUtil.createToken(user);
            response.put("token", token);
            response.put("message", "Login successful");
            Map<String, String> userInfo = new HashMap<>();
            userInfo.put("username", user.getName());
            userInfo.put("uuid", user.getUuid());
            response.put("user", userInfo);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid username or password");
            return ResponseEntity.status(401).body(response);
        }
    }
}
