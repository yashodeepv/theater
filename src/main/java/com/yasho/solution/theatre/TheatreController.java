package com.yasho.solution.theatre;

import com.yasho.solution.entity.Showtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/theatres")
public class TheatreController {

    @Autowired
    private TheatreService theatreService;

    @PostMapping("/{theatreId}/create-showtime")
    public ResponseEntity<Showtime> createShowtime(
            @PathVariable Long theatreId,
            @RequestParam Long movieId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime showDateTime,
            @RequestParam(name = "price") Integer price) {
        Showtime showtime = theatreService.createShowtime(theatreId, movieId, showDateTime, price);
        return ResponseEntity.ok(showtime);
    }

    @PutMapping("/update-showtime/{showtimeId}")
    public ResponseEntity<Showtime> updateShowtime(
            @PathVariable Long showtimeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime newShowDateTime) {
        Showtime showtime = theatreService.updateShowtime(showtimeId, newShowDateTime);
        return ResponseEntity.ok(showtime);
    }

    @DeleteMapping("/delete-showtime/{showtimeId}")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long showtimeId) {
        theatreService.deleteShowtime(showtimeId);
        return ResponseEntity.noContent().build();
    }
}
