package com.yasho.solution.booking;

public class BookingException extends Exception {
    public BookingException(String invalid_movie_id) {
        super(invalid_movie_id);
    }
}
