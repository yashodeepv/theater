package com.yasho.solution.offer;

import com.yasho.solution.entity.Offer;
import com.yasho.solution.entity.Showtime;

public class ThirdTicketDiscountOfferStrategy implements OfferStrategy {

    @Override
    public double calculateDiscountAmount(Showtime showtime, int numberOfTickets, Offer offer) {
        int discountTicketsCount = numberOfTickets / 3;
        return discountTicketsCount * showtime.getTicketPrice() * offer.getDiscountPercentage();
    }
}
