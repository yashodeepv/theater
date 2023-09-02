package com.yasho.solution.booking;

import com.yasho.solution.entity.*;
import com.yasho.solution.offer.OfferStrategy;
import com.yasho.solution.offer.OfferStrategyFactory;
import com.yasho.solution.repo.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final MovieRepo movieRepo;

    private final TheatreRepo theatreRepo;

    private final ShowtimeRepo showtimeRepo;

    private final BookingRepo bookingRepo;

    private final SeatRepo seatRepo;

    public BookingService(MovieRepo movieRepo, TheatreRepo theatreRepo, ShowtimeRepo showtimeRepo, BookingRepo bookingRepo, SeatRepo seatRepo) {
        this.movieRepo = movieRepo;
        this.theatreRepo = theatreRepo;
        this.showtimeRepo = showtimeRepo;
        this.bookingRepo = bookingRepo;
        this.seatRepo = seatRepo;
    }


    public BookingDTO bookTicketsWithDiscount(BookingDTO bookingDTO) throws BookingException {
        Movie movie = movieRepo.findByTitle(bookingDTO.getMovieTitle())
                .orElseThrow(() -> new BookingException("Invalid movie"));

        Theatre theatre = theatreRepo.findByName(bookingDTO.getTheatre())
                .orElseThrow(() -> new BookingException("Invalid theatre"));

        Showtime showtime = showtimeRepo.findByMovieAndShowDate(movie, bookingDTO.getShowtime())
                .orElseThrow(() -> new BookingException("Invalid showtime"));

        List<Seat> seats = bookingDTO.getSeatNumber().stream().map(a -> seatRepo.findByShowtimeAndSeatNumber(showtime, a)).filter(Optional::isPresent).map(a -> a.orElseThrow(() -> new RuntimeException("Missing sear"))).collect(Collectors.toList());

        bookingDTO.setNumberOfTickets(seats.size());

        List<Offer> applicableOffers = showtime.getOffers();

        if(!seats.stream().allMatch(Seat::isAvailable)) {
            throw new BookingException("Seats Not available");
        }
        double ticketPrice = calculateTicketPrice(showtime);
        double discountAmount = calculateTotalDiscountAmount(showtime, bookingDTO.getNumberOfTickets(), applicableOffers);
        double totalAmount = ticketPrice * bookingDTO.getNumberOfTickets();
        double finalAmount = totalAmount - discountAmount;

        com.yasho.solution.entity.Booking booking = new com.yasho.solution.entity.Booking();
        booking.setShowtime(showtime);
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setSeats(seats);
        booking.setTotalAmount(totalAmount);
        booking.setDiscountAmount(discountAmount);
        booking.setFinalAmount(finalAmount);
        bookingRepo.save(booking);
        seats.forEach(a -> {
            a.setAvailable(false);
            seatRepo.saveAndFlush(a);
        });
        bookingDTO.setDiscountAmount(discountAmount);
        bookingDTO.setFinalAmount(finalAmount);
        bookingDTO.setTotalAmount(totalAmount);
        return bookingDTO;
    }

    private double calculateTicketPrice(Showtime showtime) {
        return showtime.getTicketPrice();
    }

    private double calculateTotalDiscountAmount(Showtime showtime, int numberOfTickets, List<Offer> applicableOffers) {
        double totalDiscountAmount = 0.0;
        for (Offer offer : applicableOffers) {
            totalDiscountAmount += applyDiscount(offer, showtime, numberOfTickets);
        }
        return totalDiscountAmount;
    }

    private double applyDiscount(Offer offer, Showtime showtime, int numberOfTickets) {
        OfferStrategy offerStrategy = OfferStrategyFactory.getInstance(offer.getOfferType());
        return offerStrategy.calculateDiscountAmount(showtime, numberOfTickets, offer);
    }
}
