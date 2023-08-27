package com.yasho.solution.offer;

import com.yasho.solution.entity.OfferType;

public class OfferStrategyFactory {
    public static OfferStrategy getInstance(OfferType offerType) {
        switch (offerType) {
            case THIRD_TICKET_DISCOUNT:
                return new ThirdTicketDiscountOfferStrategy();
            case AFTERNOON_SHOW_DISCOUNT:
                return new AfternoonShowDiscountOfferStrategy();
            default:
                throw new IllegalArgumentException("Unsupported offer type: " + offerType);
        }
    }
}
