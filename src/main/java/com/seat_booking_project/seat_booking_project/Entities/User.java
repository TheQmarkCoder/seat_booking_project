package com.seat_booking_project.seat_booking_project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user") // Matches the table name in the database
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_ID; // Primary Key

    @Column(length = 30)
    private String username;

    @Column(length = 20)
    private String password;

    @Column(length = 20)
    private String location;

    @Column(name = "phone_number", nullable = true)
    private Long phone_number; // Changed to Long to handle larger values

    @Column(length = 20, unique = true)
    private String email;

    @Column(length = 10)
    private String role;

    // Getters and setters
    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(Long phone_number) {
        this.phone_number = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
