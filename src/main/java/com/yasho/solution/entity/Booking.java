package com.yasho.solution.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id", nullable = false)
    private Showtime showtime;

    @OneToMany
    @JoinTable(name = "booking_seat")
    private List<Seat> seats;

    @Column(name = "booking_date")
    private LocalDateTime bookingDateTime;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "final_amount")
    private double finalAmount;

}
