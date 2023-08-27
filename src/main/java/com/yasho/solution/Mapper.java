package com.yasho.solution;

import com.yasho.solution.dto.MovieDTO;
import com.yasho.solution.dto.ShowtimeDTO;
import com.yasho.solution.dto.TheatreDTO;
import com.yasho.solution.entity.Movie;
import com.yasho.solution.entity.Showtime;
import com.yasho.solution.entity.Theatre;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {

    public TheatreDTO toDto(Theatre theatre) {
        return TheatreDTO.builder()
                .location(theatre.getLocation())
                .seatingCapacity(theatre.getSeatingCapacity())
                .name(theatre.getName())
                .showtimes(theatre.getShowtimes().stream().map(this::toDto).collect(Collectors.toList()))
                .build();
    }

    public MovieDTO toDto(Movie movie) {
        return MovieDTO.builder()
                .genre(movie.getGenre())
                .language(movie.getLanguage())
                .title(movie.getTitle())
                .build();
    }
    public ShowtimeDTO toDto(Showtime showtime) {
        return ShowtimeDTO.builder()
                .movie(toDto(showtime.getMovie()))
                .showDate(showtime.getShowDate())
                .build();
    }
}
