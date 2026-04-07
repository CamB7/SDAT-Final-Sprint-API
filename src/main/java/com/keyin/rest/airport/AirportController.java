package com.keyin.rest.airport;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AirportController {

	private final AirportService airportService;

	public AirportController(AirportService airportService) {
		this.airportService = airportService;
	}

	@GetMapping("/airports")
	public Iterable<Airport> getAllAirports() {
		return airportService.getAllAirports();
	}

	@GetMapping("/airports/{id}")
	public Optional<Airport> getAirportById(@PathVariable Long id) {
		return airportService.getAirportById(id);
	}

	@GetMapping("/airports/code/{code}")
	public Optional<Airport> getAirportByCode(@PathVariable String code) {
		return airportService.getAirportByCode(code);
	}

	@PostMapping("/airports")
	public Airport createAirport(@RequestBody Airport airport) {
		return airportService.createAirport(airport);
	}

	@PutMapping("/airports/{id}")
	public Airport updateAirport(@PathVariable Long id, @RequestBody Airport airport) {
		return airportService.updateAirport(id, airport);
	}

	@DeleteMapping("/airports/{id}")
	public void deleteAirport(@PathVariable Long id) {
		airportService.deleteAirportById(id);
	}
}
