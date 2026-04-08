package com.keyin.rest.aircraft;

import com.keyin.rest.aircraft.dto.AircraftDto;
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
public class AircraftControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private AircraftController aircraftController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(aircraftController).build();
    }

    @Test
    void getAllAircrafts_returnsJson() throws Exception {
        AircraftDto a = new AircraftDto(); a.id = 1L; a.type = "A320";
        when(aircraftService.getAllAircraftsDto()).thenReturn(List.of(a));

        mockMvc.perform(get("/aircrafts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
