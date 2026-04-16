package com.keyin.rest.flight;

import com.keyin.rest.aircraft.Aircraft;
import com.keyin.rest.aircraft.AircraftRepository;
import com.keyin.rest.airport.Airport;
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
public class FlightServiceUnitTest {

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

    private Gate gate;
    private Flight flight;
    private Aircraft aircraft;
    private Airport airportTake;
    private Airport airportLand;

    @BeforeEach
    void setUp() {
        gate = new Gate();
        gate.setId(2L);
        gate.setCode("G1");
        gate.setTerminal("T1");

		aircraft = new Aircraft("A320", "TestAir", 150, "TA100");
 		aircraft.setId(1L);

        airportTake = new Airport();
        airportTake.setId(1L);
        airportTake.setCode("TKT");
        airportTake.setName("TakeoffIntl");

        airportLand = new Airport();
        airportLand.setId(3L);
        airportLand.setCode("LND");
        airportLand.setName("LandingIntl");

        flight = new Flight();
        flight.setId(10L);
        flight.setAircraft(aircraft);
        flight.setAirportTakeOff(airportTake);
        flight.setAirportLanding(airportLand);
        flight.setScheduledDeparture(LocalDateTime.now().plusHours(2));
        flight.setScheduledArrival(LocalDateTime.now().plusHours(4));
        flight.setStatus(FlightStatus.SCHEDULED);
    }

    @Test
    void assignDepartureGate_whenGateOccupied_throws() {
        when(flightRepository.findByDepartureGateIdAndStatusIn(eq(2L), anyList())).thenReturn(List.of(new Flight()));

        assertThatThrownBy(() -> flightService.assignDepartureGate(10L, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("currently occupied");

        verify(flightRepository, never()).save(any());
    }

    @Test
    void assignDepartureGate_whenFree_assignsAndBoards() {
        when(flightRepository.findByDepartureGateIdAndStatusIn(eq(2L), anyList())).thenReturn(List.of());
        when(flightRepository.findById(10L)).thenReturn(Optional.of(flight));
        when(gateService.getGateById(2L)).thenReturn(Optional.of(gate));
        when(flightRepository.save(any(Flight.class))).thenAnswer(i -> i.getArgument(0));

        FlightDto dto = flightService.assignDepartureGate(10L, 2L);

        assertThat(dto).isNotNull();
        assertThat(dto.departureGateCode).isEqualTo("G1");
        assertThat(dto.status).isEqualTo(FlightStatus.BOARDING);

        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void assignArrivalGate_whenGateOccupied_throws() {
        when(flightRepository.findByArrivalGateIdAndStatusIn(eq(2L), anyList())).thenReturn(List.of(new Flight()));

        assertThatThrownBy(() -> flightService.assignArrivalGate(10L, 2L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("currently occupied");

        verify(flightRepository, never()).save(any());
    }

    @Test
    void assignArrivalGate_whenFree_assignsAndAtGateIfLanded() {
        when(flightRepository.findByArrivalGateIdAndStatusIn(eq(2L), anyList())).thenReturn(List.of());
        when(flightRepository.findById(10L)).thenReturn(Optional.of(flight));
        when(gateService.getGateById(2L)).thenReturn(Optional.of(gate));
        when(flightRepository.save(any(Flight.class))).thenAnswer(i -> i.getArgument(0));

        // simulate flight is LANDED so assignArrivalGate will set AT_GATE
        flight.setStatus(FlightStatus.LANDED);

        FlightDto dto = flightService.assignArrivalGate(10L, 2L);

        assertThat(dto).isNotNull();
        assertThat(dto.arrivalGateCode).isEqualTo("G1");
        assertThat(dto.status).isEqualTo(FlightStatus.AT_GATE);

        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void updateStatus_departed_setsActualDeparture() {
        when(flightRepository.findById(10L)).thenReturn(Optional.of(flight));
        when(flightRepository.save(any(Flight.class))).thenAnswer(i -> i.getArgument(0));

        FlightDto dto = flightService.updateStatus(10L, FlightStatus.DEPARTED);

        assertThat(dto).isNotNull();
        assertThat(dto.status).isEqualTo(FlightStatus.DEPARTED);
        assertThat(dto.actualDeparture).isNotNull();

        verify(flightRepository).save(any(Flight.class));
    }

    @Test
    void createFlightFromRequest_missingAircraft_throws() {
        CreateFlightRequest req = new CreateFlightRequest();
        req.setAircraftId(999L);
        req.setScheduledDeparture(LocalDateTime.now().plusDays(1));
        req.setScheduledArrival(LocalDateTime.now().plusDays(1).plusHours(2));

        when(aircraftRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> flightService.createFlightFromRequest(req))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Aircraft not found");
    }

    @Test
    void createFlightFromRequest_success_creates() {
        CreateFlightRequest req = new CreateFlightRequest();
        req.setAircraftId(1L);
        req.setDepartureAirportCode(airportTake.getCode());
        req.setArrivalAirportCode(airportLand.getCode());
        LocalDateTime sd = LocalDateTime.now().plusDays(1);
        LocalDateTime sa = sd.plusHours(2);
        req.setScheduledDeparture(sd);
        req.setScheduledArrival(sa);

        when(aircraftRepository.findById(1L)).thenReturn(Optional.of(aircraft));
        when(airportRepository.findByCode("TKT")).thenReturn(Optional.of(airportTake));
        when(airportRepository.findByCode("LND")).thenReturn(Optional.of(airportLand));
        when(flightRepository.save(any(Flight.class))).thenAnswer(i -> {
            Flight f = i.getArgument(0);
            f.setId(99L);
            return f;
        });

        FlightDto dto = flightService.createFlightFromRequest(req);

        assertThat(dto).isNotNull();
        assertThat(dto.id).isEqualTo(99L);
        assertThat(dto.departureAirportId).isEqualTo(1L);
        assertThat(dto.arrivalAirportId).isEqualTo(3L);
        assertThat(dto.status).isEqualTo(FlightStatus.SCHEDULED);
    }
}
