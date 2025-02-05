package com.seat_booking_project.seat_booking_project.Repository;

import com.seat_booking_project.seat_booking_project.Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    @Query("SELECT m.movieName FROM Movie m WHERE m.movieId = :movieId")
    String findMovieNameById(@Param("movieId") Integer movieId);

}
