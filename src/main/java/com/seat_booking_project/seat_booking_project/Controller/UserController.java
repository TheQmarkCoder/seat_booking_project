package com.seat_booking_project.seat_booking_project.Controller;

import com.seat_booking_project.seat_booking_project.Entities.User;
import com.seat_booking_project.seat_booking_project.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserService userService;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/{user_ID}")
    public ResponseEntity<User> getUserById(@PathVariable("user_ID") int id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Optional<User> foundUser = userService.getUserByEmail(user.getEmail());

        // Check if user exists and password matches directly (no bcrypt)
        if (foundUser.isPresent() && foundUser.get().getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>(foundUser.get(), HttpStatus.OK); // Successful login
        } else {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED); // Authentication failure
             }
    }

    @PostMapping
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        try {
            // Ensure required fields are not null or empty
            if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
                    user.getPassword() == null || user.getPassword().trim().isEmpty() ||
                    user.getEmail() == null || user.getEmail().trim().isEmpty() ||
                    user.getRole() == null || user.getRole().trim().isEmpty()) {
                return new ResponseEntity<>("Missing required fields", HttpStatus.BAD_REQUEST);
            }

            // Validate email format
            if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                return new ResponseEntity<>("Invalid email format", HttpStatus.BAD_REQUEST);
            }

            // Validate email uniqueness
            Optional<User> existingUser = userService.getUserByEmail(user.getEmail());
            if (existingUser.isPresent()) {
                return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
            }

            // Save the user
            User savedUser = userService.addUser(user);

            // Return userId, username, and email in the response
            Map<String, Object> response = new HashMap<>();
            response.put("user_ID", savedUser.getUser_ID());
            response.put("username", savedUser.getUsername());
            response.put("email", savedUser.getEmail());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();  // Log the error
            return new ResponseEntity<>("Error adding user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{user_ID}")
    public ResponseEntity<String> updateUser(@PathVariable("user_ID") int id, @RequestBody User user) {
        try {
            // Ensure that the user_ID in the request body matches the path variable
            if (user.getUser_ID() != id) {
                user.setUser_ID(id);  // Overwrite with the path variable ID to avoid inconsistency
            }

            // Check if the user exists
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                userService.updateUser(user);  // Proceed with updating the user
                return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);  // User doesn't exist
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the error
            return new ResponseEntity<>("Error updating user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }




    // Delete a user by ID
    @DeleteMapping("/{user_ID}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_ID") int id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }

    // Delete all users
    @DeleteMapping("/clearAll")
    public ResponseEntity<String> deleteAllUsers() {
        userService.deleteAllUsers();
        return new ResponseEntity<>("All users deleted successfully", HttpStatus.OK);
    }
}
