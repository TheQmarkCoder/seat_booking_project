package com.seat_booking_project.seat_booking_project.Services;

import com.seat_booking_project.seat_booking_project.Entities.Review;
import com.seat_booking_project.seat_booking_project.Repository.EventRepository;
import com.seat_booking_project.seat_booking_project.Repository.MovieRepository;
import com.seat_booking_project.seat_booking_project.Repository.ReviewRepository;
import com.seat_booking_project.seat_booking_project.Repository.UserRepository;
import com.seat_booking_project.seat_booking_project.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private EventRepository eventRepository;

    // Convert Review entity to ReviewDTO
    private ReviewDTO convertToDTO(Review review) {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setReviewID(review.getReview_ID());
        reviewDTO.setUserID(review.getUser().getUser_ID());

        if (review.getMovie() != null) {
            reviewDTO.setMovieID(review.getMovie().getMovieId());
        }

        if (review.getEvent() != null) {
            reviewDTO.setEventID(review.getEvent().getEventID());
        }

        reviewDTO.setReviewText(review.getReview());
        return reviewDTO;
    }

    // Convert ReviewDTO to Review entity
    private Review convertToEntity(ReviewDTO reviewDTO) {
        Review review = new Review();
        review.setReview_ID(reviewDTO.getReviewID());
        review.setReview(reviewDTO.getReviewText());

        if (reviewDTO.getUserID() != null) {
            review.setUser(userRepository.findById(reviewDTO.getUserID())
                    .orElseThrow(() -> new RuntimeException("User not found")));
        }

        if (reviewDTO.getMovieID() != null) {
            review.setMovie(movieRepository.findById(reviewDTO.getMovieID()).orElse(null));
        }

        if (reviewDTO.getEventID() != null) {
            review.setEvent(eventRepository.findById(reviewDTO.getEventID()).orElse(null));
        }

        return review;
    }

    // Get all reviews
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Get review by ID
    public ReviewDTO getReviewById(Integer reviewID) {
        Review review = reviewRepository.findById(reviewID)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return convertToDTO(review);
    }

    // Add a new review
    public void addReview(ReviewDTO reviewDTO) {
        Review review = convertToEntity(reviewDTO);
        reviewRepository.save(review);
    }

    // Update an existing review
    public void updateReview(int id, ReviewDTO updatedReviewDTO) {
        Review existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found with ID: " + id));

        existingReview.setReview(updatedReviewDTO.getReviewText());
        if (updatedReviewDTO.getMovieID() != null) {
            existingReview.setMovie(movieRepository.findById(updatedReviewDTO.getMovieID()).orElse(null));
        }
        if (updatedReviewDTO.getEventID() != null) {
            existingReview.setEvent(eventRepository.findById(updatedReviewDTO.getEventID()).orElse(null));
        }
        reviewRepository.save(existingReview);
    }

    // Delete a review by ID
    public void deleteReview(Integer reviewID) {
        if (!reviewRepository.existsById(reviewID)) {
            throw new RuntimeException("Review not found");
        }
        reviewRepository.deleteById(reviewID);
    }
}
