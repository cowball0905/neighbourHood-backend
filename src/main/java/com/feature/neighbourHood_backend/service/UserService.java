package com.feature.neighbourHood_backend.service;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.feature.neighbourHood_backend.model.CustomUserDetails;
import com.feature.neighbourHood_backend.model.entity.Role;
import com.feature.neighbourHood_backend.model.entity.User;
import com.feature.neighbourHood_backend.repository.RoleRepository;
import com.feature.neighbourHood_backend.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.encoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        return new CustomUserDetails(user, user.getRoles());
    }

    public boolean findByUsername(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean findById(UUID uuid) {
        if (userRepository.findById(uuid).isPresent()) {
            return true;
        }
        return false;
    }

    public boolean register(String username, String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        Optional<Role> role = roleRepository.findByName("USER");

        if (user.isPresent()) {
            return false;
        } else if (role.isPresent()) {
            User rUser = new User(username, email, encoder.encode(password), role.get());
            userRepository.save(rUser);
            return true;
        } else {
            return false;
        }
    }

    public User getUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));

        return user;
    }
}
