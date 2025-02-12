package com.seat_booking_project.seat_booking_project.Repository;

import com.seat_booking_project.seat_booking_project.Entities.Event;
import com.seat_booking_project.seat_booking_project.Entities.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seats, Integer> {
    // Custom query to find seats by theaterID and surrogate
    @Query("SELECT s FROM Seats s WHERE s.theater.theaterId.theaterID = :theaterID AND s.theater.theaterId.surrogate = :surrogate")
    List<Seats> findSeatsByTheaterAndSurrogate(@Param("theaterID") Integer theaterID, @Param("surrogate") Integer surrogate);
    @Query("SELECT s FROM Seats s WHERE s.rowID = ?1")
    Seats findByRowId(Integer rowId);

    @Query("SELECT s FROM Seats s WHERE s.eventID = :eventID")
    List<Seats> findSeatsByEvent(@Param("eventID") Event eventID);

}
