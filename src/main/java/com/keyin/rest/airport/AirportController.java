package com.keyin.rest.airport;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import com.keyin.rest.airport.dto.AirportDto;

@RestController
public class AirportController {

	private final AirportService airportService;

	public AirportController(AirportService airportService) {
		this.airportService = airportService;
	}

	@GetMapping("/airports")
	public List<AirportDto> getAllAirports() {
		return airportService.getAllAirportsDto();
	}

	@GetMapping("/airports/{id}")
	public ResponseEntity<com.keyin.rest.airport.dto.AirportDto> getAirportById(@PathVariable Long id) {
		var dto = airportService.getAirportDtoById(id);
		if (dto == null) return ResponseEntity.notFound().build();
		return ResponseEntity.ok(dto);
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
