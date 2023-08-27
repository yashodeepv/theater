package com.yasho.solution.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDTO {
    private Long id;
    private String movieTitle;
    private String theatre;
    private String showtime;
    private List<Integer> seatNumber;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;
    private int numberOfTickets;

}
