package com.keyin.rest.airport;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AirportService {
	private final AirportRepository airportRepository;

	public AirportService(AirportRepository airportRepository) {
		this.airportRepository = airportRepository;
	}

	public Airport createAirport(Airport airport) {
		return airportRepository.save(airport);
	}

	public Iterable<Airport> getAllAirports() {
		return airportRepository.findAll();
	}

	public Optional<Airport> getAirportById(Long id) {
		return airportRepository.findById(id);
	}

	public Optional<Airport> getAirportByCode(String code) {
		return airportRepository.findByCode(code);
	}

	public Airport updateAirport(Long id, Airport updated) {
		Airport existing = airportRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("Airport not found: " + id));

		if (updated.getName() != null) {
			existing.setName(updated.getName());
		}
		if (updated.getCode() != null) {
			existing.setCode(updated.getCode());
		}

		return airportRepository.save(existing);
	}

	public void deleteAirportById(Long id) {
		if (!airportRepository.existsById(id)) {
			throw new IllegalArgumentException("Airport not found: " + id);
		}
		airportRepository.deleteById(id);
	}

	// convenience method matching test and controller expectations
	public void deleteAirport(Long id) {
		deleteAirportById(id);
	}
}
