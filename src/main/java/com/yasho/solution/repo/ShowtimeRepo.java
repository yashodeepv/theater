package com.yasho.solution.repo;

import com.yasho.solution.entity.Movie;
import com.yasho.solution.entity.Showtime;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ShowtimeRepo extends JpaRepository<Showtime, Long> {
    List<Showtime> findByMovieAndShowDateBetween(Movie movie, LocalDateTime startDate, LocalDateTime endDate);
    Optional<Showtime> findByMovieAndShowDate(Movie movie, LocalDateTime showTime);
}
