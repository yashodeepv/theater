package com.yasho.solution.dto;

import com.yasho.solution.entity.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowtimeDTO {
    private LocalDateTime showDate;
    private MovieDTO movie;
    private TheatreDTO theatre;
    private List<SeatDTO> seats;
    private int ticketPrice;
}
