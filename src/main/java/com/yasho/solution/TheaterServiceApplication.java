package com.yasho.solution;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yasho.solution.entity.*;
import com.yasho.solution.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class TheaterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheaterServiceApplication.class, args);
	}



	@Bean
		public ObjectMapper objectMapper() {
			return new ObjectMapper()
					.setAnnotationIntrospector(new JacksonAnnotationIntrospector())
					.registerModule(new JavaTimeModule())
					.setDateFormat(new StdDateFormat())
					.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		}



	@Bean
	public CommandLineRunner cml(MovieRepo movieRepo, ShowtimeRepo showtimeRepo, SeatRepo seatRepo, TheatreRepo theatreRepo, OfferRepository offerRepository, BookingRepo bookingRepo) {
		return a -> {

			List<String> theatreNames = Arrays.asList("INOX,Thane", "Cinepolis,Thane", "PVR,Thane", "PVR,Mumbai", "IMAX,Thane");
			List<String> movieNames = Arrays.asList("English,SciFi,Oppenheimer", "Hindi,Comedy,Golmaal","Marathi,Comedy,XYZ");

			Theatre theatre = Theatre.builder()
					.location("Mumbai")
					.name("PVR")
					.seatingCapacity(100)
					.build();
			Movie movie = Movie.builder()
					.language("English")
					.genre("SciFi")
					.title("Oppenheimer")
					.build();
			Theatre theatre1 = Theatre.builder()
					.location("Thane")
					.name("INOX")
					.seatingCapacity(100)
					.build();
			Movie movie1 = Movie.builder()
					.language("Hindi")
					.genre("Comedy")
					.title("Golmaal")
					.build();
			Offer offer = Offer.builder()
					.startDate(LocalDate.of(2022, Month.JULY, 20))
					.endDate(LocalDate.of(2024, Month.SEPTEMBER, 20))
					.offerName("Afternoon")
					.offerType(OfferType.AFTERNOON_SHOW_DISCOUNT)
					.discountPercentage(0.5)
					.build();
			Offer offer1 = Offer.builder()
					.startDate(LocalDate.of(2022, Month.JULY, 20))
					.endDate(LocalDate.of(2024, Month.SEPTEMBER, 20))
					.offerName("ThirdTicketFree")
					.offerType(OfferType.THIRD_TICKET_DISCOUNT)
					.discountPercentage(1)
					.build();
			Showtime showtime = Showtime.builder()
					.theatre(theatre)
					.movie(movie)
					.offers(Arrays.asList(offer))
					.ticketPrice(1000)
					.showDate(LocalDateTime.of(2023, Month.AUGUST, 10, 13, 0))
					.build();

			Showtime showtime1 = Showtime.builder()
					.theatre(theatre1)
					.movie(movie1)
					.offers(Arrays.asList(offer, offer1))
					.ticketPrice(1000)
					.showDate(LocalDateTime.of(2023, Month.JULY, 10, 18, 0))
					.build();
			Showtime showtime2 = Showtime.builder()
					.theatre(theatre1)
					.movie(movie)
					.offers(Arrays.asList(offer1))
					.ticketPrice(1000)
					.showDate(LocalDateTime.of(2023, Month.JULY, 10, 14, 0))
					.build();

			offerRepository.saveAndFlush(offer);
			offerRepository.saveAndFlush(offer1);
			theatreRepo.saveAndFlush(theatre);
			theatreRepo.saveAndFlush(theatre1);
			movieRepo.saveAndFlush(movie);
			movieRepo.saveAndFlush(movie1);
			showtimeRepo.saveAndFlush(showtime);
			showtimeRepo.saveAndFlush(showtime1);
			showtimeRepo.saveAndFlush(showtime2);

			for(int i = 1;i <= 100; i++) {
				Seat seat = Seat.builder()
						.seatNumber(i)
						.showtime(showtime)
						.isAvailable(true)
						.build();
				seatRepo.saveAndFlush(seat);
			}
			for(int i = 1;i <= 100; i++) {
				Seat seat = Seat.builder()
						.seatNumber(i)
						.showtime(showtime1)
						.isAvailable(true)
						.build();
				seatRepo.saveAndFlush(seat);
			}
			for(int i = 1;i <= 100; i++) {
				Seat seat = Seat.builder()
						.seatNumber(i)
						.showtime(showtime2)
						.isAvailable(true)
						.build();
				seatRepo.saveAndFlush(seat);
			}
		};
	}

}
