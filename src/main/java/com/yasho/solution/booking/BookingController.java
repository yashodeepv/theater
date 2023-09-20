package com.yasho.solution.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> book(@RequestBody BookingDTO bookingDTORequest) throws BookingException {
        BookingDTO bookingDTO = bookingService.bookTicketsWithDiscount(bookingDTORequest);
        return ResponseEntity.ok(bookingDTO);
    }
}
