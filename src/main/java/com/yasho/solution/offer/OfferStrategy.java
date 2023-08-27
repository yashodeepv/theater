package com.yasho.solution.offer;

import com.yasho.solution.entity.Offer;
import com.yasho.solution.entity.Showtime;

public interface OfferStrategy {
    double calculateDiscountAmount(Showtime showtime, int numberOfTickets, Offer offer);
}
