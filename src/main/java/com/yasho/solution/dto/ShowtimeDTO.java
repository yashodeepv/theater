package com.yasho.solution.dto;

import com.yasho.solution.entity.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ShowtimeDTO {
    private Long id;
    private LocalDateTime showDate;
    private MovieDTO movie;
    private TheatreDTO theatre;
    private List<SeatDTO> seats;
    private int ticketPrice;
    private List<OfferDTO> offers;
}
