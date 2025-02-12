package com.seat_booking_project.seat_booking_project.Controller;

import com.seat_booking_project.seat_booking_project.dto.PaymentDTO;
import com.seat_booking_project.seat_booking_project.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // Create a new payment
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        // Pass the PaymentDTO directly to the service
        PaymentDTO savedPaymentDTO = paymentService.savePayment(paymentDTO);

        // Return the saved PaymentDTO in the response
        return ResponseEntity.ok(savedPaymentDTO);
    }

    // Get all payments
    @GetMapping
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    // Get a payment by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable int id) {
        PaymentDTO paymentDTO = paymentService.getPaymentById(id);
        return paymentDTO != null ? ResponseEntity.ok(paymentDTO) : ResponseEntity.notFound().build();
    }

    // Update a payment
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updatePayment(@PathVariable int id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updatedPaymentDTO = paymentService.updatePayment(id, paymentDTO);
        return updatedPaymentDTO != null ? ResponseEntity.ok(updatedPaymentDTO) : ResponseEntity.notFound().build();
    }

    // Delete a payment
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
