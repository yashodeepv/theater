package com.yasho.solution.movie;

import com.yasho.solution.entity.Movie;
import com.yasho.solution.entity.Showtime;
import com.yasho.solution.entity.Theatre;
import com.yasho.solution.repo.MovieRepo;
import com.yasho.solution.repo.ShowtimeRepo;
import com.yasho.solution.repo.TheatreRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private final TheatreRepo theatreRepository;

    private final ShowtimeRepo showtimeRepository;

    private final MovieRepo movieRepo;

    public MovieService(TheatreRepo theatreRepository, ShowtimeRepo showtimeRepository, MovieRepo movieRepo) {
        this.theatreRepository = theatreRepository;
        this.showtimeRepository = showtimeRepository;
        this.movieRepo = movieRepo;
    }

    public List<Theatre> getTheatresByMovieAndDate(String movieName, LocalDate fromDate, LocalDate toDate) {
        if(fromDate == null) {
            fromDate = LocalDateTime.MIN.toLocalDate();
        }
        if(toDate == null) {
            toDate = LocalDateTime.MAX.toLocalDate();
        }
        final LocalDateTime from = fromDate.atStartOfDay();
        final LocalDateTime to = toDate.atStartOfDay();
        return movieRepo.findByTitle(movieName)
                .map(a -> showtimeRepository.findByMovieAndShowDateBetween(a, from, to).stream().map(Showtime::getTheatre).filter(Objects::nonNull).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    public List<Movie> getMovies() {
        return movieRepo.findAll();
    }




}
