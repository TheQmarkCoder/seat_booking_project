package com.seat_booking_project.seat_booking_project.Repository;

import com.seat_booking_project.seat_booking_project.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
