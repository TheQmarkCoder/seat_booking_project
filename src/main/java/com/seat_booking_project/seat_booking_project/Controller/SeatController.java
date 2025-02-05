package com.seat_booking_project.seat_booking_project.Controller;

import com.seat_booking_project.seat_booking_project.Entities.Seats;
import com.seat_booking_project.seat_booking_project.Services.SeatService;
import com.seat_booking_project.seat_booking_project.dto.SeatDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seats")
public class SeatController {
    private static final Logger logger = LoggerFactory.getLogger(SeatController.class);

    private final SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping
    public ResponseEntity<SeatDTO> createSeat(@RequestBody SeatDTO seatDTO) {
        logger.info("Attempting to create seat: {}", seatDTO);
        try {
            // Ensure that the POST method does not update existing rows
            if (seatDTO.getRowID() != null) {
                Optional<Seats> existingSeat = seatService.getSeatById(seatDTO.getRowID());
                if (existingSeat.isPresent()) {
                    // Return a conflict response as row already exists
                    return new ResponseEntity<>(null, HttpStatus.CONFLICT);
                }
            }

            // Save the new seat and convert it to DTO
            SeatDTO savedSeatDTO = seatService.saveSeat(seatDTO);
            logger.info("Successfully created seat: {}", savedSeatDTO);
            return new ResponseEntity<>(savedSeatDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating seat: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{rowID}")
    public ResponseEntity<SeatDTO> getSeatById(@PathVariable Integer rowID) {
        Optional<Seats> seat = seatService.getSeatById(rowID);
        return seat.map(s -> ResponseEntity.ok(convertEntityToDTO(s)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{rowID}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Integer rowID) {
        seatService.deleteSeat(rowID);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/event/{eventID}")
    public ResponseEntity<List<SeatDTO>> getSeatsByEvent(@PathVariable Integer eventID) {
        List<Seats> seats = seatService.getSeatsByEvent(eventID);
        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();  // No seats found for the event
        }
        List<SeatDTO> seatDTOs = convertEntityListToDTOList(seats);
        return ResponseEntity.ok(seatDTOs);  // Return list of SeatDTOs for the event
    }


    @GetMapping("/theater/{theaterID}/surrogate/{surrogate}")
    public ResponseEntity<List<SeatDTO>> getSeatsByTheaterAndSurrogate(
            @PathVariable Integer theaterID,
            @PathVariable Integer surrogate) {
        List<Seats> seats = seatService.getSeatsByTheaterAndSurrogate(theaterID, surrogate);
        if (seats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<SeatDTO> seatDTOs = convertEntityListToDTOList(seats);
        return ResponseEntity.ok(seatDTOs);
    }

    @PutMapping("/{rowID}")
    public ResponseEntity<SeatDTO> updateSeat(@PathVariable Integer rowID, @RequestBody SeatDTO seatDTO) {
        SeatDTO updatedSeatDTO = seatService.updateSeat(rowID, seatDTO);
        return updatedSeatDTO != null ? ResponseEntity.ok(updatedSeatDTO) : ResponseEntity.notFound().build();
    }

    // Helper Methods to Convert Entities to DTOs
    private SeatDTO convertEntityToDTO(Seats seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setRowID(seat.getRowID());
        seatDTO.setSeatNumbers(seat.getSeatNumbers());
        seatDTO.setReservedSeats(seat.getReservedSeats());
        seatDTO.setSelectedSeats(seat.getSelectedSeats());
        seatDTO.setPricePerSeat(seat.getPricePerSeat());
        seatDTO.setEventID(seat.getEventID() != null ? seat.getEventID().getEventID() : null);
        seatDTO.setTheaterId(seat.getTheaterId());

        return seatDTO;
    }

    private List<SeatDTO> convertEntityListToDTOList(List<Seats> seats) {
        return seats.stream().map(this::convertEntityToDTO).toList();
    }
}
