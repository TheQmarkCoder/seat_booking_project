package com.seat_booking_project.seat_booking_project.Services;

import com.seat_booking_project.seat_booking_project.Entities.Event;
import com.seat_booking_project.seat_booking_project.Entities.Seats;
import com.seat_booking_project.seat_booking_project.Repository.EventRepository;
import com.seat_booking_project.seat_booking_project.Repository.SeatRepository;
import com.seat_booking_project.seat_booking_project.dto.TicketBookingDTO;
import com.seat_booking_project.seat_booking_project.Entities.TicketBooking;
import com.seat_booking_project.seat_booking_project.Repository.TicketBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketBookingService {

    private final TicketBookingRepository ticketBookingRepository;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    public TicketBookingService(TicketBookingRepository ticketBookingRepository) {
        this.ticketBookingRepository = ticketBookingRepository;
    }

    public TicketBooking saveTicketBooking(TicketBookingDTO ticketBookingDTO) {
        // Convert DTO to Entity
        TicketBooking ticketBooking = toEntity(ticketBookingDTO);
        return ticketBookingRepository.save(ticketBooking);
    }

    // Get All Ticket Bookings
    public List<TicketBookingDTO> getAllTicketBookings() {
        List<TicketBooking> ticketBookings = ticketBookingRepository.findAll();
        return ticketBookings.stream().map(this::toDTO).toList();
    }

    // Get Ticket Booking by ID
    public Optional<TicketBookingDTO> getTicketBookingById(Integer bookingId) {
        Optional<TicketBooking> ticketBooking = ticketBookingRepository.findById(bookingId);
        return ticketBooking.map(this::toDTO);
    }

        // Update Ticket Booking
    public TicketBookingDTO updateTicketBooking(Integer bookingId, TicketBookingDTO updatedBookingDTO) {
        if (ticketBookingRepository.existsById(bookingId)) {
            // Convert DTO to Entity and set the ID for update
            updatedBookingDTO.setBookingId(bookingId);
            TicketBooking updatedBooking = toEntity(updatedBookingDTO);
            updatedBooking = ticketBookingRepository.save(updatedBooking);
            return toDTO(updatedBooking);
        } else {
            throw new RuntimeException("Ticket Booking not found with ID " + bookingId);
        }
    }

    // Delete Ticket Booking
    public void deleteTicketBooking(Integer bookingId) {
        if (ticketBookingRepository.existsById(bookingId)) {
            ticketBookingRepository.deleteById(bookingId);
        } else {
            throw new RuntimeException("Ticket Booking not found with ID " + bookingId);
        }
    }

    // Convert Entity to DTO
    public TicketBookingDTO toDTO(TicketBooking ticketBooking) {
        TicketBookingDTO dto = new TicketBookingDTO();
        dto.setBookingId(ticketBooking.getBookingId());
        dto.setDate(ticketBooking.getDate());
        dto.setNoOfTickets(ticketBooking.getNoOfTickets());
        dto.setStatus(ticketBooking.getStatus());
        dto.setUserId(ticketBooking.getUserId());

        // Adding the rowId from Seat entity
        if (ticketBooking.getEventId() != null) {
            dto.setEventId(ticketBooking.getEventId().getEventID());  // Assuming getRowId() method in Seats entity
        }

        if (ticketBooking.getSeat() != null) {
            dto.setRowId(ticketBooking.getSeat().getRowID());  // Assuming getRowId() method in Seats entity
        }

        return dto;
    }

    // Convert DTO to Entity
    public TicketBooking toEntity(TicketBookingDTO ticketBookingDTO) {
        TicketBooking ticketBooking = new TicketBooking();
        ticketBooking.setBookingId(ticketBookingDTO.getBookingId());
        ticketBooking.setDate(ticketBookingDTO.getDate());
        ticketBooking.setNoOfTickets(ticketBookingDTO.getNoOfTickets());
        ticketBooking.setStatus(ticketBookingDTO.getStatus());
        ticketBooking.setUserId(ticketBookingDTO.getUserId());

        // Look up the Seat entity by rowId and set it to the TicketBooking
        if (ticketBookingDTO.getRowId() != null) {
            // Find the Seat by rowId
            Seats seat = seatRepository.findByRowId(ticketBookingDTO.getRowId());  // Assumes this method exists
            if (seat != null) {
                ticketBooking.setSeat(seat);  // Link the found Seat to the TicketBooking
            } else {
                // Optionally, throw an error or handle the case where the seat doesn't exist
                throw new RuntimeException("Seat with rowId " + ticketBookingDTO.getRowId() + " not found.");
            }
        }

        if (ticketBookingDTO.getEventId() != null) {
            // Find the Seat by rowId
            Event eventId = eventRepository.findByEventId(ticketBookingDTO.getEventId());  // Assumes this method exists
            if (eventId != null) {
                ticketBooking.setEventId(eventId);  // Link the found Seat to the TicketBooking
            } else {
                // Optionally, throw an error or handle the case where the seat doesn't exist
                throw new RuntimeException("Event with EventID " + ticketBookingDTO.getEventId() + " not found.");
            }
        }

        return ticketBooking;
    }
    public TicketBooking getBookingById(int bookingId) {
        Optional<TicketBooking> booking = ticketBookingRepository.findById(bookingId);
        return booking.orElse(null);
    }
}
