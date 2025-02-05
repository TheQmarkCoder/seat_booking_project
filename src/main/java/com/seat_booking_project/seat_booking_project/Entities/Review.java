package com.seat_booking_project.seat_booking_project.Entities;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {

    @Id
    @Column(name="review_ID")
    private int review_ID;

    @ManyToOne
    @JoinColumn(name = "user_ID", referencedColumnName = "user_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "movie_ID", referencedColumnName = "movie_ID", nullable = true)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "event_ID", referencedColumnName = "event_ID", nullable = true)
    private Event event;

    @Column(name = "review")
    private String review;

    // Getters and Setters
    public int getReview_ID() {
        return review_ID;
    }

    public void setReview_ID(int review_ID) {
        this.review_ID = review_ID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}