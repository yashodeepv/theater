package com.yasho.solution.repo;

import com.yasho.solution.entity.Seat;
import com.yasho.solution.entity.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepo extends JpaRepository<Seat, Long> {
    Optional<Seat> findByShowtimeAndSeatNumber(Showtime showtime, Integer seatNumber);
}
