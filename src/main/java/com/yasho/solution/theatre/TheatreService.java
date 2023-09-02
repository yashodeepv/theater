package com.yasho.solution.theatre;

import com.yasho.solution.dto.OfferDTO;
import com.yasho.solution.entity.*;
import com.yasho.solution.repo.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TheatreService {

    private final TheatreRepo theatreRepository;

    private final ShowtimeRepo showtimeRepository;

    private final MovieRepo movieRepository;

    private final SeatRepo seatRepo;

    private final OfferRepository offerRepository;

    public TheatreService(TheatreRepo theatreRepository, ShowtimeRepo showtimeRepository, MovieRepo movieRepository, SeatRepo seatRepo, OfferRepository offerRepository) {
        this.theatreRepository = theatreRepository;
        this.showtimeRepository = showtimeRepository;
        this.movieRepository = movieRepository;
        this.seatRepo = seatRepo;
        this.offerRepository = offerRepository;
    }


    public List<Theatre> getAllTheaters() {
        return theatreRepository.findAll();
    }

    public Showtime createShowtime(String theatreName, String movieTitle, LocalDateTime showDateTime, Integer price, List<OfferDTO> offers1) {
        Theatre theatre = theatreRepository.findByName(theatreName)
                .orElseThrow(() -> new RuntimeException("Theatre not found: " + theatreName));

        Movie movie = movieRepository.findByTitle(movieTitle)
                .orElseThrow(() -> new RuntimeException("Movie not found : " + movieTitle));

        Showtime showtime = new Showtime();
        showtime.setTheatre(theatre);
        showtime.setMovie(movie);
        showtime.setShowDate(showDateTime);
        showtime.setTicketPrice(price);

        List<Offer> offers = offers1.stream().map(a -> Offer.builder().offerName(a.getOfferName())
                .offerType(a.getOfferType())
                .startDate(a.getStartDate())
                .discountPercentage(a.getDiscountPercentage())
                .endDate(a.getEndDate())
                .build()).toList();

        for(Offer offer : offers) {
            offerRepository.saveAndFlush(offer);
        }
        showtime.setOffers(offers);

        Showtime showtime1 = showtimeRepository.save(showtime);
        for(int i = 0; i < theatre.getSeatingCapacity(); i++) {
            seatRepo.saveAndFlush(Seat.builder().seatNumber(i+1).isAvailable(true).showtime(showtime1).build());
        }
        return showtime1;
    }

    public Showtime updateShowtime(Long showtimeId, LocalDateTime newShowDateTime) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new RuntimeException("Showtime not found with ID: " + showtimeId));
        showtime.setShowDate(newShowDateTime);
        return showtimeRepository.save(showtime);
    }

    public void deleteShowtime(Long showtimeId) {
        showtimeRepository.deleteById(showtimeId);
    }
}
