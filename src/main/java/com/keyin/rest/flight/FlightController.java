package com.keyin.rest.flight;

import com.keyin.rest.flight.dto.CreateFlightRequest;
import com.keyin.rest.flight.dto.FlightDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {this.flightService = flightService;}

	@GetMapping("/flights")
	public List<com.keyin.rest.flight.dto.FlightDto> getAllFlights() {
		return flightService.getAllFlightsDto();
	}

	@GetMapping("/flights/{id}")
	public ResponseEntity<FlightDto> getFlightById(@PathVariable long id) {
        FlightDto dto = flightService.getFlightDtoById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

	// New: create via DTO
	@PostMapping("/flights")
	public FlightDto createFlight(@Valid @RequestBody CreateFlightRequest req) {
		return flightService.createFlightFromRequest(req);
	}

	@DeleteMapping("/flights/{id}")
	public void deleteFlight(@PathVariable long id) {
		flightService.deleteFlightById(id);
	}

	// New: update via DTO
	@PutMapping("/flights/{id}")
	public FlightDto updateFlight(@PathVariable Long id, @Valid @RequestBody CreateFlightRequest req) {
		return flightService.updateFlightFromRequest(id, req);
	}

    // airport board endpoints
    @GetMapping("/airports/{airportId}/arrivals/at-gate")
    public List<FlightDto> getArrivalsAtGate(@PathVariable Long airportId) {
        return flightService.getArrivalsAtGate(airportId);
    }

    @GetMapping("/airports/{airportId}/arrivals/in-flight")
    public List<FlightDto> getArrivalsInFlight(@PathVariable Long airportId) {
        return flightService.getArrivalsInFlight(airportId);
    }

    @GetMapping("/airports/{airportId}/departures/scheduled")
    public List<FlightDto> getScheduledDepartures(@PathVariable Long airportId) {
        return flightService.getScheduledDepartures(airportId);
    }

    @GetMapping("/airports/{airportId}/departures/scheduled-between")
    public List<FlightDto> getScheduledDeparturesBetween(@PathVariable Long airportId,
                                                          @RequestParam LocalDateTime from,
                                                          @RequestParam LocalDateTime to) {
        return flightService.getScheduledDeparturesBetween(airportId, from, to);
    }

    // assign gates and update status
    @PostMapping("/flights/{flightId}/assign-departure-gate/{gateId}")
    public FlightDto assignDepartureGate(@PathVariable Long flightId, @PathVariable Long gateId) {
        return flightService.assignDepartureGate(flightId, gateId);
    }

    @PostMapping("/flights/{flightId}/assign-arrival-gate/{gateId}")
    public FlightDto assignArrivalGate(@PathVariable Long flightId, @PathVariable Long gateId) {
        return flightService.assignArrivalGate(flightId, gateId);
    }

    @PutMapping("/flights/{flightId}/status")
    public FlightDto updateFlightStatus(@PathVariable Long flightId, @RequestParam FlightStatus status) {
        return flightService.updateStatus(flightId, status);
    }

}
