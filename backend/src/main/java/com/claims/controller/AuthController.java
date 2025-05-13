package com.claims.controller;

import com.claims.dto.RegisterRequest;
import com.claims.model.User;
import com.claims.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User savedUser = userService.registerUser(request);
        return ResponseEntity.ok(savedUser);
    }
    @GetMapping("/ping")
    public String test() {
        return "Auth controller is active!";
    }
}

