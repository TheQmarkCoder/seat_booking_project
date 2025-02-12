package com.seat_booking_project.seat_booking_project.Services;

import com.seat_booking_project.seat_booking_project.Entities.TicketBooking;
import com.seat_booking_project.seat_booking_project.dto.PaymentDTO;
import com.seat_booking_project.seat_booking_project.Entities.Payment;
import com.seat_booking_project.seat_booking_project.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private TicketBookingService ticketBookingService;

    // Fetch all payments and convert them to PaymentDTO
    public List<PaymentDTO> getAllPayments() {
        List<Payment> payments = paymentRepository.findAll();
        return payments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Fetch a payment by ID and convert it to PaymentDTO
    public PaymentDTO getPaymentById(int id) {
        return paymentRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // Save a payment and return the PaymentDTO
    public PaymentDTO savePayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setPaymentAmount(paymentDTO.getPaymentAmount());
        payment.setPaymentType(paymentDTO.getPaymentType());
        payment.setPaymentStatus(paymentDTO.isPaymentStatus());
        payment.setDate(paymentDTO.getDate());


        TicketBooking booking = ticketBookingService.getBookingById(paymentDTO.getBookingId());
        payment.setBookingId(booking);

        Payment savedPayment = paymentRepository.save(payment);

        return convertToDTO(savedPayment);
    }

    // Update a payment and return the updated PaymentDTO
    public PaymentDTO updatePayment(int id, PaymentDTO paymentDTO) {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            Payment payment = optionalPayment.get();
            payment.setPaymentAmount(paymentDTO.getPaymentAmount());
            payment.setPaymentType(paymentDTO.getPaymentType());
            payment.setPaymentStatus(paymentDTO.isPaymentStatus());
            payment.setDate(paymentDTO.getDate());

            // Update the Booking entity if necessary (similar to save method)
            // For example:
            TicketBooking booking = ticketBookingService.getBookingById(paymentDTO.getBookingId());
            payment.setBookingId(booking);

            paymentRepository.save(payment);
            return convertToDTO(payment);
        }
        return null;
    }

    // Delete a payment by ID
    public void deletePayment(int id) {
        paymentRepository.deleteById(id);
    }

    // Utility method to convert Payment entity to PaymentDTO
    private PaymentDTO convertToDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setPaymentAmount(payment.getPaymentAmount());
        paymentDTO.setPaymentType(payment.getPaymentType());
        paymentDTO.setPaymentStatus(payment.isPaymentStatus());
        paymentDTO.setDate(payment.getDate());

        // Set the bookingId as an integer (if you want to store only the booking ID in the DTO)
        if (payment.getBookingId() != null) {
            paymentDTO.setBookingId(payment.getBookingId().getBookingId());
        }

        return paymentDTO;
    }
}
