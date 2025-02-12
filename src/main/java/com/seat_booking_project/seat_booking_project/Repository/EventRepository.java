package com.seat_booking_project.seat_booking_project.Repository;

import com.seat_booking_project.seat_booking_project.Entities.Event;
import com.seat_booking_project.seat_booking_project.Entities.Seats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("SELECT e FROM Event e WHERE e.eventID = ?1")
    Event findByEventId(Integer eventId);
    @Query("SELECT e FROM Event e WHERE e.eventID = ?1")
    Event findByEventID(Event eventId);
    @Query("SELECT e.eventName FROM Event e WHERE e.eventID = :eventId")
    String findEventNameById(@Param("eventId") Integer eventId);

}
