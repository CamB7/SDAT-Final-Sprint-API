package com.keyin.rest.flight;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class FlightController {

	private final FlightService flightService;

	public FlightController(FlightService flightService) {this.flightService = flightService;}

	@GetMapping("/flights")
	public Iterable<com.keyin.rest.flight.Flight> getAllFlights() {
		return flightService.getAllFlights();
	}

	@GetMapping("/flights/{id}")
	public Optional<com.keyin.rest.flight.Flight> getFlightById(@PathVariable long id) {
		return flightService.getFlightById(id);
	}

	@PostMapping("/flights")
	public com.keyin.rest.flight.Flight createFlight(@RequestBody com.keyin.rest.flight.Flight flight) {
		return flightService.createFlight(flight);
	}

	@DeleteMapping("/flights/{id}")
	public void deleteFlight(@PathVariable long id) {
		flightService.deleteFlightById(id);
	}

	@PutMapping("/flights/{id}")
	public com.keyin.rest.flight.Flight updateFlight(@PathVariable Long id, @RequestBody com.keyin.rest.flight.Flight flight) {
		return flightService.updateFlight(id, flight);
	}

}
