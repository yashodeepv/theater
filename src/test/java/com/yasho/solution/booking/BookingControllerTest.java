package com.yasho.solution.booking;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.yasho.solution.repo.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BookingService bookingService;

    @MockBean
    MovieRepo movieRepo;

    @MockBean
    ShowtimeRepo showtimeRepo;

    @MockBean
    SeatRepo seatRepo;

    @MockBean
    TheatreRepo theatreRepo;

    @MockBean
    OfferRepository offerRepository;

    @MockBean
    BookingRepo bookingRepo;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setUp() {
        // Initialize the MockMvc instance
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testBook() throws Exception {
        // given
        BookingDTO requestDTO = new BookingDTO();
        BookingDTO mockResponseDTO = new BookingDTO();
        when(bookingService.bookTicketsWithDiscount(any(BookingDTO.class))).thenReturn(mockResponseDTO);

        // when
        mockMvc.perform(post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isOk());

        // then
        verify(bookingService, times(1)).bookTicketsWithDiscount(eq(requestDTO));
    }
}
