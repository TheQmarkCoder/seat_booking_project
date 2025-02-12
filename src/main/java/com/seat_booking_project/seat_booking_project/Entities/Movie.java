package com.seat_booking_project.seat_booking_project.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;



@Entity
@Table(name = "movie")
public class Movie {

    @Id
    @Column(name = "movie_ID")
    private int movieId;

    @Column(name = "movie_name")
    private String movieName;

    @Column(name = "cast")
    private String cast;

    @Column(name = "genre")
    private String genre;

    @Column(name = "age_rating")
    private String ageRating;

    @Column(name = "language")
    private String language;

    @Column(name = "duration")
    private String duration;

    @Column(name = "ratings")
    private double ratings;

    @Column(name = "description" ,length = 2000  )
    private String description;

    @JsonProperty("image_name")
    @Column(name = "imageName", length = 100)
    private String imageName;

    // Getters and Setters

    public int getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getCast() {
        return cast;
    }

    public String getGenre() {
        return genre;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public String getLanguage() {
        return language;
    }

    public String getDuration() {
        return duration;
    }

    public double getRatings() {
        return ratings;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setRatings(double ratings) {
        this.ratings = ratings;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
