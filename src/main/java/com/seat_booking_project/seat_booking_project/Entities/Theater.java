package com.seat_booking_project.seat_booking_project.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "theater")
public class Theater {

    @EmbeddedId
    private TheaterId theaterId;

    @Column(name = "theater_name")
    private String theaterName;

    @Column(name = "location")
    private String location;

    @Column(name = "show_timings")
    private LocalDateTime showTimings;

    @ManyToOne
    @JoinColumn(name = "movie_ID" , referencedColumnName = "movie_ID")
    private Movie movieID;

    @Column(name = "screen_ID")
    private Integer screenID;

    @Column(name = "total_seats")
    private Integer totalSeats;

    @Column(name = "available_seats")
    private Integer availableSeats;

    @Column(name = "reserved_seats")
    private Integer reservedSeats;

    @Column(name = "disability_features", columnDefinition = "LONGTEXT")
    private String disabilityFeatures;


    public TheaterId getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(TheaterId theaterId) {
        this.theaterId = theaterId;
    }

    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getShowTimings() {
        return showTimings;
    }

    public void setShowTimings(LocalDateTime showTimings) {
        this.showTimings = showTimings;
    }

    public Movie getMovieID() {
        return movieID;
    }

    public void setMovieID(Movie movieID) {
        this.movieID = movieID;
    }

    public Integer getScreenID() {
        return screenID;
    }

    public void setScreenID(Integer screenID) {
        this.screenID = screenID;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getDisabilityFeatures() {
        return disabilityFeatures;
    }

    public void setDisabilityFeatures(String disabilityFeatures) {
        this.disabilityFeatures = disabilityFeatures;
    }
}
