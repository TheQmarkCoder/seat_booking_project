package com.seat_booking_project.seat_booking_project.Services;

import com.seat_booking_project.seat_booking_project.Entities.Event;
import com.seat_booking_project.seat_booking_project.Repository.EventRepository;
import com.seat_booking_project.seat_booking_project.dto.SeatDTO;
import com.seat_booking_project.seat_booking_project.Entities.Seats;
import com.seat_booking_project.seat_booking_project.Repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private EventRepository eventRepository;

    @Transactional
    public SeatDTO saveSeat(SeatDTO seatDTO) {
        // Ensure that seatDTO does not have a rowID set or it's a new entry
        Seats seat = convertDTOToEntity(seatDTO);
        Seats savedSeat = seatRepository.save(seat);
        return convertEntityToDTO(savedSeat);
    }

    public Optional<Seats> getSeatById(Integer rowID) {
        return seatRepository.findById(rowID);
    }

    public List<Seats> getSeatsByEvent(Integer eventID) {
        Event event = eventRepository.findById(eventID).orElseThrow(null);
        return seatRepository.findSeatsByEvent(event);  // Assuming the method exists in the repository
    }


    public void deleteSeat(Integer rowID) {
        seatRepository.deleteById(rowID);
    }

    public List<Seats> getSeatsByTheaterAndSurrogate(Integer theaterID, Integer surrogate) {
        return seatRepository.findSeatsByTheaterAndSurrogate(theaterID, surrogate);
    }

    public SeatDTO updateSeat(Integer rowID, SeatDTO seatDTO) {
        Optional<Seats> existingSeatOpt = seatRepository.findById(rowID);
        if (existingSeatOpt.isPresent()) {
            Seats existingSeat = existingSeatOpt.get();
            // Update fields
            existingSeat.setSeatNumbers(seatDTO.getSeatNumbers());
            existingSeat.setReservedSeats(seatDTO.getReservedSeats());
            existingSeat.setSelectedSeats(seatDTO.getSelectedSeats());
            existingSeat.setPricePerSeat(seatDTO.getPricePerSeat());

            // Fetch the Event object using eventID
            if (seatDTO.getEventID() != null) {
                Event event = eventRepository.findByEventId(seatDTO.getEventID());
                if (event != null) {
                    existingSeat.setEventID(event);  // Set the Event object
                } else {
                    throw new RuntimeException("Event with EventID " + seatDTO.getEventID() + " not found.");
                }
            }

            // Save and return updated DTO
            Seats updatedSeat = seatRepository.save(existingSeat);
            return convertEntityToDTO(updatedSeat);
        } else {
            return null; // Not found, so no update
        }
    }


    // Helper Methods for Conversion between DTO and Entity
    private Seats convertDTOToEntity(SeatDTO seatDTO) {
        Seats seat = new Seats();
        seat.setRowID(seatDTO.getRowID());
        seat.setSeatNumbers(seatDTO.getSeatNumbers());
        seat.setReservedSeats(seatDTO.getReservedSeats());
        seat.setSelectedSeats(seatDTO.getSelectedSeats());
        seat.setPricePerSeat(seatDTO.getPricePerSeat());
        seat.setTheaterId(seatDTO.getTheaterId());

        if (seatDTO.getEventID() != null) {
            // Fetch the Event object using the eventID
            Event event = eventRepository.findByEventId(seatDTO.getEventID());
            if (event != null) {
                seat.setEventID(event);  // Set the Event object
            } else {
                throw new RuntimeException("Event with EventID " + seatDTO.getEventID() + " not found.");
            }
        }

        return seat;  // Return the populated Seat entity
    }


    private SeatDTO convertEntityToDTO(Seats seat) {
        SeatDTO seatDTO = new SeatDTO();
        seatDTO.setRowID(seat.getRowID());
        seatDTO.setSeatNumbers(seat.getSeatNumbers());
        seatDTO.setReservedSeats(seat.getReservedSeats());
        seatDTO.setSelectedSeats(seat.getSelectedSeats());
        seatDTO.setPricePerSeat(seat.getPricePerSeat());
        seatDTO.setTheaterId(seat.getTheaterId());

        if (seat.getEventID() != null) {
            seatDTO.setEventID(seat.getEventID().getEventID());
        }

        return seatDTO;
    }
}
