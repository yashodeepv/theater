package com.yasho.solution.offer;

import com.yasho.solution.entity.Offer;
import com.yasho.solution.entity.Showtime;

import java.time.LocalTime;

public class AfternoonShowDiscountOfferStrategy implements OfferStrategy {

    @Override
    public double calculateDiscountAmount(Showtime showtime, int numberOfTickets, Offer offer) {
        if (isAfternoonShow(showtime)) {
            return showtime.getTicketPrice() * numberOfTickets * offer.getDiscountPercentage();
        }
        return 0.0;
    }

    private boolean isAfternoonShow(Showtime showtime) {
        LocalTime afternoonThreshold = LocalTime.of(16, 0);
        return showtime.getShowDate().toLocalTime().isBefore(afternoonThreshold);
    }
}
