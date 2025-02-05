package com.seat_booking_project.seat_booking_project.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "seats")
public class Seats {

    @Id
    @Column(name = "row_ID")
    private Integer rowID;

    @Embedded
    private TheaterId theaterId;

    @Column(name = "seat_numbers", columnDefinition = "LONGTEXT")
    private String seatNumbers;

    @Column(name = "reserved_seats", columnDefinition = "LONGTEXT")
    private String reservedSeats;

    @Column(name = "selected_seats", columnDefinition = "LONGTEXT")
    private String selectedSeats;

    @Column(name = "price_per_seat")
    private Integer pricePerSeat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_ID", referencedColumnName = "event_ID")
    private Event eventID;


    @Version
    private Long version = 1L;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "theater_ID", referencedColumnName = "theater_ID", insertable = false, updatable = false),
            @JoinColumn(name = "surrogate", referencedColumnName = "surrogate", insertable = false, updatable = false)
    })

    @JsonIgnore
    private Theater theater;

    public Integer getRowID() {
        return rowID;
    }

    public void setRowID(Integer rowID) {
        this.rowID = rowID;
    }

    public TheaterId getTheaterId() {
        return theaterId;
    }

    public void setTheaterId(TheaterId theaterId) {
        this.theaterId = theaterId;
    }

    public String getSeatNumbers() {
        return seatNumbers;
    }

    public void setSeatNumbers(String seatNumbers) {
        this.seatNumbers = seatNumbers;
    }

    public String getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(String reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getSelectedSeats() {
        return selectedSeats;
    }

    public void setSelectedSeats(String selectedSeats) {
        this.selectedSeats = selectedSeats;
    }

    public Integer getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(Integer pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public Event getEventID() {
        return eventID;
    }

    public void setEventID(Event eventID) {
        this.eventID = eventID;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }
}
