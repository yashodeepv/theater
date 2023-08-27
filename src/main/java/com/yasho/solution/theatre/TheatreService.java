package com.yasho.solution.theatre;

import com.yasho.solution.entity.Movie;
import com.yasho.solution.entity.Showtime;
import com.yasho.solution.entity.Theatre;
import com.yasho.solution.repo.MovieRepo;
import com.yasho.solution.repo.ShowtimeRepo;
import com.yasho.solution.repo.TheatreRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TheatreService {

    @Autowired
    private TheatreRepo theatreRepository;

    @Autowired
    private ShowtimeRepo showtimeRepository;

    @Autowired
    private MovieRepo movieRepository;

    public Showtime createShowtime(Long theatreId, Long movieId, LocalDateTime showDateTime, Integer price) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new RuntimeException("Theatre not found with ID: " + theatreId));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with ID: " + movieId));

        Showtime showtime = new Showtime();
        showtime.setTheatre(theatre);
        showtime.setMovie(movie);
        showtime.setShowDate(showDateTime);
        showtime.setTicketPrice(price);
        return showtimeRepository.save(showtime);
    }

    public Showtime updateShowtime(Long showtimeId, LocalDateTime newShowDateTime) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found with ID: " + showtimeId));
        showtime.setShowDate(newShowDateTime);
        return showtimeRepository.save(showtime);
    }

    public void deleteShowtime(Long showtimeId) {
        showtimeRepository.deleteById(showtimeId);
    }
}
