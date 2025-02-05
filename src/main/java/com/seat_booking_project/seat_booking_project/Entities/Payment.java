package com.seat_booking_project.seat_booking_project.Entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment") // Matches your table name in the database
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_ID")
    private int paymentId;

    @OneToOne
    @JoinColumn(name = "booking_ID", referencedColumnName = "booking_ID", nullable = false)
    private TicketBooking booking;

    @Column(name="payment_amount", nullable = false)
    private int paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType paymentType;

    @Column(nullable = false)
    private boolean paymentStatus;

    @Temporal(TemporalType.DATE)
    private Date date;

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public TicketBooking getBookingId() {
        return booking;
    }

    public void setBookingId(TicketBooking booking) {
        this.booking = booking;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
