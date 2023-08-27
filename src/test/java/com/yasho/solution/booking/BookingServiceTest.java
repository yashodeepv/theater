package com.yasho.solution.booking;

import com.yasho.solution.entity.*;
import com.yasho.solution.repo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
    private OfferRepository offerRepository;

    @Test
    public void testBookTicketsWithDiscount() throws BookingException {
        // Sample input data for the test
        Long movieId = 1L;
        Long theatreId = 2L;
        Long showtimeId = 3L;
        int numberOfTickets = 4;

        // Mock the Movie, Theatre, and Showtime entities
        Movie movie = new Movie();
        movie.setId(movieId);
        when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));

        Theatre theatre = new Theatre();
        theatre.setId(theatreId);
        when(theatreRepo.findById(theatreId)).thenReturn(Optional.of(theatre));

        Showtime showtime = new Showtime();
        showtime.setId(showtimeId);
        showtime.setTicketPrice(40); // Assume ticket price for the showtime.
        when(showtimeRepo.findById(showtimeId)).thenReturn(Optional.of(showtime));

        // Mock the Offer entity and OfferRepository
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setOfferType(OfferType.THIRD_TICKET_DISCOUNT);
        offer.setDiscountPercentage(0.5);
        List<Offer> applicableOffers = Collections.singletonList(offer);
        when(offerRepository.findActiveOffers(movie, theatre, showtime)).thenReturn(applicableOffers);

        // Call the method under test
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieId(movieId).showtimeId(showtimeId).theatreId(theatreId).numberOfTickets(numberOfTickets)
                .build();
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingRequest);

        // Assertions for the expected results
        assertNotNull(bookingDTO);
        assertEquals(4, bookingDTO.getNumberOfTickets());
        assertEquals(160.0, bookingDTO.getTotalAmount(), 0.001);
        assertEquals(20.0, bookingDTO.getDiscountAmount(), 0.001);
        assertEquals(140.0, bookingDTO.getFinalAmount(), 0.001);

        // Verify that the booking is saved to the repository
        verify(bookingRepo, times(1)).save(any(com.yasho.solution.entity.Booking.class));
    }

    @Test
    public void testBookTicketsWithDiscount_AfterNoon() throws BookingException {
        // Sample input data for the test
        Long movieId = 1L;
        Long theatreId = 2L;
        Long showtimeId = 3L;
        int numberOfTickets = 4;

        // Mock the Movie, Theatre, and Showtime entities
        Movie movie = new Movie();
        movie.setId(movieId);
        when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));

        Theatre theatre = new Theatre();
        theatre.setId(theatreId);
        when(theatreRepo.findById(theatreId)).thenReturn(Optional.of(theatre));

        Showtime showtime = new Showtime();
        showtime.setId(showtimeId);
        showtime.setShowDate(LocalDateTime.of(2022, 2, 2, 13, 0));
        showtime.setTicketPrice(40); // Assume ticket price for the showtime.
        when(showtimeRepo.findById(showtimeId)).thenReturn(Optional.of(showtime));

        // Mock the Offer entity and OfferRepository
        Offer offer = new Offer();
        offer.setId(1L);
        offer.setOfferType(OfferType.AFTERNOON_SHOW_DISCOUNT);
        offer.setDiscountPercentage(0.5);
        List<Offer> applicableOffers = Collections.singletonList(offer);
        when(offerRepository.findActiveOffers(movie, theatre, showtime)).thenReturn(applicableOffers);

        // Call the method under test
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieId(movieId)
                .theatreId(theatreId)
                .showtimeId(showtimeId)
                .numberOfTickets(numberOfTickets)
                .build();
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingRequest);

        // Assertions for the expected results
        assertNotNull(bookingDTO);
        assertEquals(4, bookingDTO.getNumberOfTickets());
        assertEquals(160.0, bookingDTO.getTotalAmount(), 0.001);
        assertEquals(80.0, bookingDTO.getDiscountAmount(), 0.001);
        assertEquals(80.0, bookingDTO.getFinalAmount(), 0.001);

        // Verify that the booking is saved to the repository
        verify(bookingRepo, times(1)).save(any(com.yasho.solution.entity.Booking.class));
    }


    @Test
    public void testBookTicketsWithoutDiscount() throws BookingException {
        // Sample input data for the test
        Long movieId = 1L;
        Long theatreId = 2L;
        Long showtimeId = 3L;
        int numberOfTickets = 4;

        // Mock the Movie, Theatre, and Showtime entities
        Movie movie = new Movie();
        movie.setId(movieId);
        when(movieRepo.findById(movieId)).thenReturn(Optional.of(movie));

        Theatre theatre = new Theatre();
        theatre.setId(theatreId);
        when(theatreRepo.findById(theatreId)).thenReturn(Optional.of(theatre));

        Showtime showtime = new Showtime();
        showtime.setId(showtimeId);
        showtime.setShowDate(LocalDateTime.of(2022, 2, 2, 13, 0));
        showtime.setTicketPrice(40); // Assume ticket price for the showtime.
        when(showtimeRepo.findById(showtimeId)).thenReturn(Optional.of(showtime));

        // Mock the Offer entity and OfferRepository
        when(offerRepository.findActiveOffers(movie, theatre, showtime)).thenReturn(Collections.emptyList());

        // Call the method under test
        BookingDTO bookingRequest = BookingDTO.builder()
                .movieId(movieId).theatreId(theatreId).showtimeId(showtimeId).numberOfTickets(numberOfTickets)
                .build();
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingRequest);

        // Assertions for the expected results
        assertNotNull(bookingDTO);
        assertEquals(4, bookingDTO.getNumberOfTickets());
        assertEquals(160.0, bookingDTO.getTotalAmount(), 0.001);
        assertEquals(0.0, bookingDTO.getDiscountAmount(), 0.001);
        assertEquals(160.0, bookingDTO.getFinalAmount(), 0.001);

        // Verify that the booking is saved to the repository
        verify(bookingRepo, times(1)).save(any(com.yasho.solution.entity.Booking.class));
    }
}
