package com.seat_booking_project.seat_booking_project.Services;

import com.seat_booking_project.seat_booking_project.Entities.User;
import com.seat_booking_project.seat_booking_project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get user by ID
    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User addUser(User user) {
        return userRepository.save(user); // Return the saved user object
    }

    // Update an existing user
    public void updateUser(User user) {
        userRepository.save(user);
    }

    // Delete a user by ID
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    // Delete all users
    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    // Get user by email (for email validation)
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
