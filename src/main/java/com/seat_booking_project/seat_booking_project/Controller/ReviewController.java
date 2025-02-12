package com.seat_booking_project.seat_booking_project.Controller;

import com.seat_booking_project.seat_booking_project.Services.ReviewService;
import com.seat_booking_project.seat_booking_project.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // Add a new review
    @PostMapping
    public ResponseEntity<String> addReview(@RequestBody ReviewDTO reviewDTO) {
        if (reviewDTO.getUserID() == null) {
            return ResponseEntity.badRequest().body("User ID must be provided.");
        }
        if (reviewDTO.getMovieID() != null && reviewDTO.getEventID() != null) {
            return ResponseEntity.badRequest().body("Either movie ID or event ID must be provided, not both.");
        }
        if (reviewDTO.getMovieID() == null && reviewDTO.getEventID() == null) {
            return ResponseEntity.badRequest().body("Either movie ID or event ID must be provided.");
        }
        reviewService.addReview(reviewDTO);
        return ResponseEntity.ok("Review Added Successfully");
    }

    // Update an existing review
    @PutMapping("/{review_ID}")
    public ResponseEntity<String> updateReview(@PathVariable("review_ID") int id, @RequestBody ReviewDTO updatedReviewDTO) {
        if (updatedReviewDTO.getUserID() == null) {
            return ResponseEntity.badRequest().body("User ID must be provided.");
        }
        if (updatedReviewDTO.getMovieID() != null && updatedReviewDTO.getEventID() != null) {
            return ResponseEntity.badRequest().body("Either movie ID or event ID must be provided, not both.");
        }
        if (updatedReviewDTO.getMovieID() == null && updatedReviewDTO.getEventID() == null) {
            return ResponseEntity.badRequest().body("Either movie ID or event ID must be provided.");
        }
        reviewService.updateReview(id, updatedReviewDTO);
        return ResponseEntity.ok("Review Updated Successfully");
    }

    // Get a review by ID
    @GetMapping("/{review_ID}")
    public ResponseEntity<?> getReviewById(@PathVariable("review_ID") int id) {
        ReviewDTO review = reviewService.getReviewById(id);
        if (review == null) {
            return ResponseEntity.status(404).body("Review not found with ID: " + id);
        }
        return ResponseEntity.ok(review);
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    // Delete a review by ID
    @DeleteMapping("/{review_ID}")
    public ResponseEntity<String> deleteReviewById(@PathVariable("review_ID") int id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok("Review deleted successfully");
    }
}
