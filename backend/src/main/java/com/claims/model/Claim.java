package com.claims.model;

import jakarta.persistence.*;

@Entity
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;
    private String description;
    private double amount;

    // Constructors
    public Claim() {}

    public Claim(String userEmail, String description, double amount) {
        this.userEmail = userEmail;
        this.description = description;
        this.amount = amount;
    }

    // Getters and setters
    public Long getId() { return id; }
    public String getUserEmail() { return userEmail; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }

    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }
    public void setDescription(String description) { this.description = description; }
    public void setAmount(double amount) { this.amount = amount; }
}
