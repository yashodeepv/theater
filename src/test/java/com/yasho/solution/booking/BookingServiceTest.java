package com.yasho.solution.booking;

import com.yasho.solution.entity.*;
import com.yasho.solution.repo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @InjectMocks
    private BookingService bookingService;

    @Mock
    private MovieRepo movieRepo;

    @Mock
    private TheatreRepo theatreRepo;

    @Mock
    private ShowtimeRepo showtimeRepo;

    @Mock
    private BookingRepo bookingRepo;

    @Mock
    private SeatRepo seatRepo;

    @Mock
    private OfferRepository offerRepository;

    @Test
    public void testBookTicketsWithDiscount_ThirdTicket() throws BookingException {
        // Mock the Movie, Theatre, and Showtime entities
        when(movieRepo.findByTitle(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Movie.builder().title("ABC").build()));
        when(theatreRepo.findByName(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Theatre.builder().build()));
        when(showtimeRepo.findByMovieAndShowDate(any(), any()))
                .thenReturn(Optional.ofNullable(Showtime.builder()
                        .showDate(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                        .offers(Arrays.asList(Offer.builder()
                                .discountPercentage(1)
                                .offerType(OfferType.THIRD_TICKET_DISCOUNT)
                                .build())).ticketPrice(1000)
                        .build()));
        when(seatRepo.findByShowtimeAndSeatNumber(any(), any()))
                .thenReturn(Optional.ofNullable(Seat.builder().seatNumber(2).isAvailable(true).build()));


        // Call the method under test
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieTitle("ABC").showtime(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                .theatre("PQR")
                .seatNumber(Arrays.asList(2,3,4))
                .build();
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingRequest);

        // Assertions for the expected results
        assertNotNull(bookingDTO);
        assertEquals(3, bookingDTO.getNumberOfTickets());
        assertEquals(3000.0, bookingDTO.getTotalAmount(), 0.001);
        assertEquals(1000.0, bookingDTO.getDiscountAmount(), 0.001);
        assertEquals(2000.0, bookingDTO.getFinalAmount(), 0.001);

        // Verify that the booking is saved to the repository
        verify(bookingRepo, times(1)).save(any(com.yasho.solution.entity.Booking.class));
    }

    @Test
    public void testBookTicketsWithDiscount_AfterNoon() throws BookingException {
        // Mock the Movie, Theatre, and Showtime entities
        when(movieRepo.findByTitle(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Movie.builder().title("ABC").build()));
        when(theatreRepo.findByName(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Theatre.builder().build()));
        when(showtimeRepo.findByMovieAndShowDate(any(), any()))
                .thenReturn(Optional.ofNullable(Showtime.builder()
                        .showDate(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                        .offers(Arrays.asList(Offer.builder()
                                .discountPercentage(0.5)
                                .offerType(OfferType.AFTERNOON_SHOW_DISCOUNT)
                                .build())).ticketPrice(1000)
                        .build()));
        when(seatRepo.findByShowtimeAndSeatNumber(any(), any()))
                .thenReturn(Optional.ofNullable(Seat.builder().seatNumber(2).isAvailable(true).build()));


        // Call the method under test
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieTitle("ABC").showtime(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                .theatre("PQR")
                .seatNumber(Arrays.asList(2,3,4))
                .build();
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingRequest);

        // Assertions for the expected results
        assertNotNull(bookingDTO);
        assertEquals(3, bookingDTO.getNumberOfTickets());
        assertEquals(3000.0, bookingDTO.getTotalAmount(), 0.001);
        assertEquals(1500.0, bookingDTO.getDiscountAmount(), 0.001);
        assertEquals(1500.0, bookingDTO.getFinalAmount(), 0.001);

        // Verify that the booking is saved to the repository
        verify(bookingRepo, times(1)).save(any(com.yasho.solution.entity.Booking.class));
    }

    @Test
    public void testBookTicketsWithDiscount_SeatNotAvailable() throws BookingException {
        // given
        when(movieRepo.findByTitle(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Movie.builder().title("ABC").build()));
        when(theatreRepo.findByName(Mockito.anyString()))
                .thenReturn(Optional.ofNullable(Theatre.builder().build()));
        when(showtimeRepo.findByMovieAndShowDate(any(), any()))
                .thenReturn(Optional.ofNullable(Showtime.builder()
                        .showDate(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                        .offers(Arrays.asList(Offer.builder()
                                .discountPercentage(0.5)
                                .offerType(OfferType.AFTERNOON_SHOW_DISCOUNT)
                                .build())).ticketPrice(1000)
                        .build()));
        when(seatRepo.findByShowtimeAndSeatNumber(any(), any()))
                .thenReturn(Optional.ofNullable(Seat.builder().seatNumber(2).isAvailable(false).build()));


        // when
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieTitle("ABC").showtime(LocalDateTime.of(2023, Month.AUGUST, 13, 13, 0, 0))
                .theatre("PQR")
                .seatNumber(Arrays.asList(2,3,4))
                .build();
        Executable executable = () -> bookingService.bookTicketsWithDiscount(bookingRequest);

        // then
        Assertions.assertThrows(BookingException.class, executable);

    }
}
