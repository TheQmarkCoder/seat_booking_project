package com.seat_booking_project.seat_booking_project.Entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "event_details")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_ID")
    private Integer eventID;

    @JsonProperty("event_name")
    @Column(name = "event_name")
    private String eventName;

    @JsonProperty("event_address")
    @Column(name = "event_address")
    private String eventAddress;

    @JsonProperty("event_artists")
    @Column(name = "event_artists", columnDefinition = "TEXT")
    private String eventArtists;

    @JsonProperty("event_type")
    @Column(name = "event_type")
    private String eventType;

    @JsonProperty("is_indoors")
    @Column(name = "is_indoors")
    private Boolean isIndoors;

    @JsonProperty("event_duration")
    @Column(name = "event_duration")
    private LocalTime eventDuration;

    @JsonProperty("date")
    @Column(name = "date")
    private LocalDateTime date;

    @JsonProperty("rating")
    @Column(name = "rating")
    private BigDecimal rating;

    @JsonProperty("available_seats")
    @Column(name = "available_seats")
    private Integer availableSeats;

    @JsonProperty("total_seats")
    @Column(name = "total_seats")
    private Integer totalSeats;

    @JsonProperty("reserved_seats")
    @Column(name = "reserved_seats")
    private Integer reservedSeats;

    @JsonProperty("disability_features")
    @Column(name = "disability_features", columnDefinition = "TEXT")
    private String disabilityFeatures;

    @JsonProperty("language")
    @Column(name = "language")
    private String language;

    @JsonProperty("age_restriction")
    @Column(name = "age_restriction")
    private Boolean ageRestriction;

    // Getters and Setters

    public Integer getEventID() {
        return eventID;
    }

    public void setEventID(Integer eventID) {
        this.eventID = eventID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventAddress() {
        return eventAddress;
    }

    public void setEventAddress(String eventAddress) {
        this.eventAddress = eventAddress;
    }

    public String getEventArtists() {
        return eventArtists;
    }

    public void setEventArtists(String eventArtists) {
        this.eventArtists = eventArtists;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Boolean getIsIndoors() {
        return isIndoors;
    }

    public void setIsIndoors(Boolean isIndoors) {
        this.isIndoors = isIndoors;
    }

    public LocalTime getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(LocalTime eventDuration) {
        this.eventDuration = eventDuration;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public Integer getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(Integer totalSeats) {
        this.totalSeats = totalSeats;
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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getAgeRestriction() {
        return ageRestriction;
    }

    public void setAgeRestriction(Boolean ageRestriction) {
        this.ageRestriction = ageRestriction;
    }
}
