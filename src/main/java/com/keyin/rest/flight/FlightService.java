package com.keyin.rest.flight;

import com.keyin.rest.flight.dto.CreateFlightRequest;
import com.keyin.rest.flight.dto.FlightDto;
import com.keyin.rest.aircraft.AircraftRepository;
import com.keyin.rest.gate.Gate;
import com.keyin.rest.gate.GateService;
import com.keyin.rest.airport.AirportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlightService {

	private final FlightRepository flightRepository;
	private final GateService gateService;
	private final AircraftRepository aircraftRepository;
	private final AirportRepository airportRepository;

    public FlightService(FlightRepository flightRepository, GateService gateService, AircraftRepository aircraftRepository, AirportRepository airportRepository) {
        this.flightRepository = flightRepository;
        this.gateService = gateService;
        this.aircraftRepository = aircraftRepository;
        this.airportRepository = airportRepository;
    }

    public Iterable<Flight> getAllFlights() {
		return flightRepository.findAll();
	}

	public Optional<Flight> getFlightById(long id) {
		return Optional.ofNullable(flightRepository.findById(id).orElse(null));
	}

	public Flight createFlight(Flight flight) {
		return flightRepository.save(flight);
	}

	public void deleteFlightById(long id) {
		Flight flightFound = flightRepository.findById(id).orElse(null);

		if (flightFound != null) {
			flightRepository.delete(flightFound);
		}
	}

	public Flight updateFlight(Long id, Flight flight) {
		Flight flightToUpdate = flightRepository.findById(id).orElse(null);
		if (flightToUpdate == null) {
			throw new IllegalArgumentException("Flight not found: " + id);
		}

		if (flight.aircraft != null) {
			flightToUpdate.aircraft = flight.aircraft;
		}

		if (flight.airportTakeOff != null) {
			flightToUpdate.airportTakeOff = flight.airportTakeOff;
		}

		if (flight.airportLanding != null) {
			flightToUpdate.airportLanding = flight.airportLanding;
		}

		return flightRepository.save(flightToUpdate);
	}

    // board-related methods returning DTOs
    public List<FlightDto> getArrivalsAtGate(Long airportId) {
        return flightRepository.findByAirportLandingIdAndStatus(airportId, FlightStatus.AT_GATE)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<FlightDto> getArrivalsInFlight(Long airportId) {
        return flightRepository.findByAirportLandingIdAndStatus(airportId, FlightStatus.IN_FLIGHT)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<FlightDto> getScheduledDepartures(Long airportId) {
        return flightRepository.findByAirportTakeOffIdAndStatus(airportId, FlightStatus.SCHEDULED)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<FlightDto> getScheduledDeparturesBetween(Long airportId, LocalDateTime from, LocalDateTime to) {
        return flightRepository.findByAirportTakeOffIdAndScheduledDepartureBetween(airportId, from, to)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    public List<FlightDto> getAllFlightsDto() {
        List<FlightDto> out = new ArrayList<>();
        for (Flight f : flightRepository.findAll()) {
            out.add(toDto(f));
        }
        return out;
    }

    // updated assignDepartureGate to check occupancy
    public FlightDto assignDepartureGate(Long flightId, Long gateId) {
        // prevent assigning a gate that's occupied by another flight (AT_GATE, BOARDING, DEPARTED?, TAXIING?)
        var occupiedStatuses = Arrays.asList(FlightStatus.AT_GATE, FlightStatus.BOARDING, FlightStatus.TAXIING, FlightStatus.DEPARTED);
        List<Flight> occupying = flightRepository.findByDepartureGateIdAndStatusIn(gateId, occupiedStatuses);
        if (occupying != null && !occupying.isEmpty()) {
            throw new IllegalArgumentException("Gate " + gateId + " is currently occupied for departure");
        }
        return assignDepartureGateOriginal(flightId, gateId);
    }

    // keep original logic in a private helper
    private FlightDto assignDepartureGateOriginal(Long flightId, Long gateId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) throw new IllegalArgumentException("Flight not found: " + flightId);

        Gate gate = gateService.getGateById(gateId).orElse(null);
        if (gate == null) throw new IllegalArgumentException("Gate not found: " + gateId);

        flight.assignDepartureGate(gate);
        // if scheduled, move to BOARDING
        if (flight.getStatus() == FlightStatus.SCHEDULED) {
            flight.setStatus(FlightStatus.BOARDING);
        }
        flight.setLastUpdated(System.currentTimeMillis());
        Flight saved = flightRepository.save(flight);
        return toDto(saved);
    }

    // updated assignArrivalGate with occupancy check
    public FlightDto assignArrivalGate(Long flightId, Long gateId) {
        var occupiedStatuses = Arrays.asList(FlightStatus.AT_GATE, FlightStatus.LANDED);
        List<Flight> occupying = flightRepository.findByArrivalGateIdAndStatusIn(gateId, occupiedStatuses);
        if (occupying != null && !occupying.isEmpty()) {
            throw new IllegalArgumentException("Gate " + gateId + " is currently occupied for arrival");
        }
        return assignArrivalGateOriginal(flightId, gateId);
    }

    private FlightDto assignArrivalGateOriginal(Long flightId, Long gateId) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) throw new IllegalArgumentException("Flight not found: " + flightId);

        Gate gate = gateService.getGateById(gateId).orElse(null);
        if (gate == null) throw new IllegalArgumentException("Gate not found: " + gateId);

        flight.assignArrivalGate(gate);
        // if landed, set AT_GATE
        if (flight.getStatus() == FlightStatus.LANDED) {
            flight.setStatus(FlightStatus.AT_GATE);
        }
        flight.setLastUpdated(System.currentTimeMillis());
        Flight saved = flightRepository.save(flight);
        return toDto(saved);
    }

    public FlightDto updateStatus(Long flightId, FlightStatus status) {
        Flight flight = flightRepository.findById(flightId).orElse(null);
        if (flight == null) throw new IllegalArgumentException("Flight not found: " + flightId);
        flight.setStatus(status);
        if (status == FlightStatus.DEPARTED) {
            flight.actualDeparture = LocalDateTime.now();
        }
        if (status == FlightStatus.LANDED) {
            flight.actualArrival = LocalDateTime.now();
        }
        flight.setLastUpdated(System.currentTimeMillis());
        Flight saved = flightRepository.save(flight);
        return toDto(saved);
    }

    public FlightDto createFlightFromRequest(CreateFlightRequest req) {
        var aircraft = aircraftRepository.findById(req.getAircraftId()).orElse(null);
        if (aircraft == null) throw new IllegalArgumentException("Aircraft not found: " + req.getAircraftId());

        Flight f = new Flight();
        f.setAircraft(aircraft);
        if (req.getAirportTakeOffId() != null) f.setAirportTakeOff(airportRepository.findById(req.getAirportTakeOffId()).orElse(null));
        if (req.getAirportLandingId() != null) f.setAirportLanding(airportRepository.findById(req.getAirportLandingId()).orElse(null));
        f.setScheduledDeparture(req.getScheduledDeparture());
        f.setScheduledArrival(req.getScheduledArrival());
        f.setStatus(FlightStatus.SCHEDULED);
        Flight saved = flightRepository.save(f);
        return toDto(saved);
    }

    public FlightDto updateFlightFromRequest(Long id, CreateFlightRequest req) {
        Flight existing = flightRepository.findById(id).orElse(null);
        if (existing == null) throw new IllegalArgumentException("Flight not found: " + id);
        if (req.getAircraftId() != null) existing.setAircraft(aircraftRepository.findById(req.getAircraftId()).orElse(null));
        if (req.getAirportTakeOffId() != null) existing.setAirportTakeOff(airportRepository.findById(req.getAirportTakeOffId()).orElse(null));
        if (req.getAirportLandingId() != null) existing.setAirportLanding(airportRepository.findById(req.getAirportLandingId()).orElse(null));
        existing.setScheduledDeparture(req.getScheduledDeparture());
        existing.setScheduledArrival(req.getScheduledArrival());
        Flight saved = flightRepository.save(existing);
        return toDto(saved);
    }

    public FlightDto getFlightDtoById(long id) {
        Flight f = flightRepository.findById(id).orElse(null);
        if (f == null) return null;
        return toDto(f);
    }

    private FlightDto toDto(Flight f) {
        FlightDto d = new FlightDto();
        d.id = f.getId();
        d.aircraftId = f.aircraft != null ? f.aircraft.getId() : null;
	    d.airlineName = f.getAircraft() != null ? f.getAircraft().getAirline() : null;
        d.status = f.getStatus();
        d.scheduledDeparture = f.scheduledDeparture;
        d.actualDeparture = f.actualDeparture;
        d.departureGateCode = f.departureGate != null ? f.departureGate.getCode() : null;
        d.departureAirportId = f.airportTakeOff != null ? f.airportTakeOff.getId() : null;
        d.departureAirportCode = f.airportTakeOff != null ? f.airportTakeOff.getCode() : null;
        d.scheduledArrival = f.scheduledArrival;
        d.actualArrival = f.actualArrival;
        d.arrivalGateCode = f.arrivalGate != null ? f.arrivalGate.getCode() : null;
        d.arrivalAirportId = f.airportLanding != null ? f.airportLanding.getId() : null;
        d.arrivalAirportCode = f.airportLanding != null ? f.airportLanding.getCode() : null;
        return d;
    }

}
