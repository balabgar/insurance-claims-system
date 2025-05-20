package com.claims.controller;

import com.claims.model.Claim;
import com.claims.repository.ClaimRepository;
import com.claims.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/claims")
public class ClaimController {

    @Autowired
    private ClaimRepository claimRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // Submit a new claim (protected)
    @PostMapping("/submit")
    public ResponseEntity<?> submitClaim(@RequestBody Claim claim,
                                         @RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            String email = claims.getSubject();

            claim.setUserEmail(email);
            Claim savedClaim = claimRepository.save(claim);

            return ResponseEntity.ok(savedClaim);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }

    // Get all claims for the logged-in user (protected)
    @GetMapping("/my")
    public ResponseEntity<?> getMyClaims(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        try {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.extractAllClaims(token);
            String email = claims.getSubject();

            List<Claim> userClaims = claimRepository.findByUserEmail(email);
            return ResponseEntity.ok(userClaims);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid or expired token");
        }
    }
}
