package com.seat_booking_project.seat_booking_project.Repository;
import com.seat_booking_project.seat_booking_project.Entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}