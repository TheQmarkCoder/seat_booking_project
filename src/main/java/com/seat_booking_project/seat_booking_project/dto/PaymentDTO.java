package com.seat_booking_project.seat_booking_project.dto;

import com.seat_booking_project.seat_booking_project.Entities.PaymentType;
import java.util.Date;

public class PaymentDTO {

    private int paymentId;
    private int paymentAmount;
    private PaymentType paymentType;
    private boolean paymentStatus;
    private Date date;
    private int bookingId;  // Changed to bookingId (integer)

    // Getter and Setter for paymentId
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    // Getter and Setter for paymentAmount
    public int getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    // Getter and Setter for paymentType
    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    // Getter and Setter for paymentStatus
    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    // Getter and Setter for date
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // Getter and Setter for bookingId (as integer)
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }
}
