package com.app.feature.auth.service;

import java.util.Optional;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.feature.auth.model.CustomUserDetails;
import com.app.feature.auth.model.Role;
import com.app.feature.auth.model.User;
import com.app.feature.auth.repository.RoleRepository;
import com.app.feature.auth.repository.UserRepository;

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

    public User findById(UUID uuid) {
        Optional<User> user = userRepository.findById(uuid);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
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
