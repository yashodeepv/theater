package com.yasho.solution.movie;

import com.yasho.solution.Mapper;
import com.yasho.solution.dto.MovieDTO;
import com.yasho.solution.dto.TheatreDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private final MovieService movieService;
    private final Mapper mapper;

    public MovieController(MovieService movieService, Mapper mapper) {
        this.movieService = movieService;
        this.mapper = mapper;
    }

    @GetMapping("/{movieId}/showtimes/theatres")
    public ResponseEntity<List<TheatreDTO>> getTheatresByMovieAndDate(
            @RequestParam String movieName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate) {
        List<TheatreDTO> theatres = movieService.getTheatresByMovieAndDate(movieName, fromDate, toDate)
                .stream().map(mapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(theatres);
    }

    @GetMapping
    public List<MovieDTO> getAllMovies() {
        return movieService.getMovies().stream().map(mapper::toDto).collect(Collectors.toList());
    }


}
