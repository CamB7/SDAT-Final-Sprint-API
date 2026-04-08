package com.keyin.rest.flight;

import com.keyin.rest.flight.dto.FlightDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FlightControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FlightService flightService;

    @InjectMocks
    private FlightController flightController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(flightController).build();
    }

    @Test
    void getArrivalsAtGate_returnsJson() throws Exception {
        FlightDto f = new FlightDto();
        f.id = 1L; f.status = FlightStatus.AT_GATE; f.scheduledArrival = LocalDateTime.now();
        when(flightService.getArrivalsAtGate(1L)).thenReturn(List.of(f));

        mockMvc.perform(get("/airports/1/arrivals/at-gate").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getAllFlights_returnsJson() throws Exception {
        FlightDto f = new FlightDto(); f.id = 2L; f.status = FlightStatus.SCHEDULED;
        when(flightService.getAllFlightsDto()).thenReturn(List.of(f));

        mockMvc.perform(get("/flights").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
