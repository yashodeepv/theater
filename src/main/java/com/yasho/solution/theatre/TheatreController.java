package com.yasho.solution.theatre;

import com.yasho.solution.Mapper;
import com.yasho.solution.dto.OfferDTO;
import com.yasho.solution.dto.ShowtimeDTO;
import com.yasho.solution.dto.TheatreDTO;
import com.yasho.solution.entity.Showtime;
import com.yasho.solution.entity.Theatre;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    private final TheatreService theatreService;
    private final Mapper mapper;
    public TheatreController(TheatreService theatreService, Mapper mapper) {
        this.theatreService = theatreService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<TheatreDTO>> getAllTheaters() {
        List<TheatreDTO> allTheaters = theatreService.getAllTheaters().stream().map(mapper::toDto).toList();
        return ResponseEntity.ok(allTheaters);
    }


    @PostMapping("/{theatreId}/create-showtime")
    public ResponseEntity<ShowtimeDTO> createShowtime(@PathVariable Long theatreId, @RequestBody ShowtimeDTO showtimeDTO) {
        Showtime showtime = theatreService.createShowtime(showtimeDTO.getTheatre().getName(), showtimeDTO.getMovie().getTitle(), showtimeDTO.getShowDate(), showtimeDTO.getTicketPrice(), showtimeDTO.getOffers());
        return ResponseEntity.ok(mapper.toDto(showtime));
    }

    @PutMapping("/{theatreId}/update-showtime/{showtimeId}")
    public ResponseEntity<ShowtimeDTO> updateShowtime(
            @PathVariable Long theatreId,
            @PathVariable Long showtimeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newShowDateTime) {
        Showtime showtime = theatreService.updateShowtime(showtimeId, newShowDateTime);
        return ResponseEntity.ok(mapper.toDto(showtime));
    }

    @DeleteMapping("/delete-showtime/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        theatreService.deleteShowtime(showtimeId);
        return ResponseEntity.noContent().build();
    }
}
