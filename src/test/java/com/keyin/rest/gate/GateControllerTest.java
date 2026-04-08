package com.keyin.rest.gate;

import com.keyin.rest.gate.dto.GateDto;
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
public class GateControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GateService gateService;

    @InjectMocks
    private GateController gateController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(gateController).build();
    }

    @Test
    void getGatesForAirport_returnsDtos() throws Exception {
        GateDto g = new GateDto();
        g.id = 1L; g.code = "A1"; g.terminal = "1"; g.airportId = 1L;
        when(gateService.getGatesDtoByAirportId(1L)).thenReturn(List.of(g));

        mockMvc.perform(get("/airports/1/gates").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}
