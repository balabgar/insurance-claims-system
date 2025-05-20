package com.claims.service;

import com.claims.dto.RegisterRequest;
import com.claims.model.User;
import com.claims.repository.UserRepository;
import com.claims.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User registerUser(RegisterRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        // Hash password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Create and save new user
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        return userRepository.save(user);
    }
    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(email);
    }

}
