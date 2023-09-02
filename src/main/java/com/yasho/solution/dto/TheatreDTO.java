package com.yasho.solution.dto;

import com.yasho.solution.entity.Showtime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TheatreDTO {
    private Long id;
    private String name;
    private String location;
    private int seatingCapacity;
    private List<ShowtimeDTO> showtimes;
}
