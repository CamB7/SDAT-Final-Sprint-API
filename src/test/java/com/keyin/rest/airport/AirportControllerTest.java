package com.keyin.rest.airport;

import com.keyin.rest.airport.dto.AirportDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AirportControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportController airportController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(airportController).build();
    }

    @Test
    void getAllAirports_returnsJson() throws Exception {
        AirportDto a = new AirportDto(); a.id = 1L; a.name = "Test"; a.code = "TST";
        when(airportService.getAllAirportsDto()).thenReturn(List.of(a));

        mockMvc.perform(get("/airports").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
