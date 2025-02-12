package com.seat_booking_project.seat_booking_project.Repository;

import com.seat_booking_project.seat_booking_project.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Method to find a user by email
    Optional<User> findByEmail(String email);
    @Query("SELECT u.username FROM User u WHERE u.user_ID = :userId")
    String findUsernameById(@Param("userId") Integer userId);

}
