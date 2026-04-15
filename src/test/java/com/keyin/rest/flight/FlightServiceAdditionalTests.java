package com.keyin.rest.flight;

import com.keyin.rest.aircraft.Aircraft;
import com.keyin.rest.airport.Airport;
import com.keyin.rest.aircraft.AircraftRepository;
import com.keyin.rest.airport.AirportRepository;
import com.keyin.rest.gate.Gate;
import com.keyin.rest.gate.GateService;
import com.keyin.rest.flight.dto.CreateFlightRequest;
import com.keyin.rest.flight.dto.FlightDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceAdditionalTests {

    @Mock
    private FlightRepository flightRepository;

    @Mock
    private GateService gateService;

    @Mock
    private AircraftRepository aircraftRepository;

    @Mock
    private AirportRepository airportRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;
    private Aircraft aircraft;
    private Airport airportTake;
    private Airport airportLand;
    private Gate gate;

    @BeforeEach
    void setUp() {
	    aircraft = new Aircraft("A320", "TestAir", 150, "TA100");
        aircraft.setId(1L);

        airportTake = new Airport();
        airportTake.setId(1L);
        airportTake.setCode("TK");
        airportTake.setName("Takeoff");

        airportLand = new Airport();
        airportLand.setId(2L);
        airportLand.setCode("LD");
        airportLand.setName("Land");

        gate = new Gate(); gate.setId(5L); gate.setCode("G5"); gate.setTerminal("T1");

        flight = new Flight();
        flight.setId(20L);
        flight.setAircraft(aircraft);
        flight.setAirportTakeOff(airportTake);
        flight.setAirportLanding(airportLand);
        flight.setScheduledDeparture(LocalDateTime.now().plusDays(1));
        flight.setScheduledArrival(LocalDateTime.now().plusDays(1).plusHours(3));
        flight.setStatus(FlightStatus.SCHEDULED);
    }

    @Test
    void assignDepartureGate_flightNotFound_throws() {
        when(flightRepository.findByDepartureGateIdAndStatusIn(eq(5L), anyList())).thenReturn(List.of());
        when(flightRepository.findById(20L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightService.assignDepartureGate(20L, 5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Flight not found");

        verify(flightRepository, never()).save(any());
    }

    @Test
    void assignDepartureGate_gateNotFound_throws() {
        when(flightRepository.findByDepartureGateIdAndStatusIn(eq(5L), anyList())).thenReturn(List.of());
        when(flightRepository.findById(20L)).thenReturn(Optional.of(flight));
        when(gateService.getGateById(5L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightService.assignDepartureGate(20L, 5L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Gate not found");

        verify(flightRepository, never()).save(any());
    }

    @Test
    void getAllFlightsDto_mapsAllFields() {
        when(flightRepository.findAll()).thenReturn(List.of(flight));

        List<FlightDto> dtos = flightService.getAllFlightsDto();

        assertThat(dtos).hasSize(1);
        FlightDto d = dtos.get(0);
        assertThat(d.id).isEqualTo(20L);
        assertThat(d.departureAirportId).isEqualTo(1L);
        assertThat(d.arrivalAirportId).isEqualTo(2L);
    }

    @Test
    void updateFlightFromRequest_missingFlight_throws() {
        when(flightRepository.findById(999L)).thenReturn(Optional.empty());
        CreateFlightRequest req = new CreateFlightRequest();
        req.setAircraftId(1L);
        req.setScheduledDeparture(LocalDateTime.now().plusDays(1));
        req.setScheduledArrival(LocalDateTime.now().plusDays(1).plusHours(2));

        assertThatThrownBy(() -> flightService.updateFlightFromRequest(999L, req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Flight not found");
    }

    @Test
    void getScheduledDeparturesBetween_delegatesToRepo_andMaps() {
        LocalDateTime from = LocalDateTime.now();
        LocalDateTime to = from.plusDays(2);
        when(flightRepository.findByAirportTakeOffIdAndScheduledDepartureBetween(1L, from, to)).thenReturn(List.of(flight));

        var list = flightService.getScheduledDeparturesBetween(1L, from, to);
        assertThat(list).hasSize(1);
        assertThat(list.get(0).departureAirportId).isEqualTo(1L);
    }
}
