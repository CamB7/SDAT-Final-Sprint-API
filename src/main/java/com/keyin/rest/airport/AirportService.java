package com.keyin.rest.airport;

import com.keyin.rest.airport.dto.AirportDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

	public List<AirportDto> getAllAirportsDto() {
		var out = new ArrayList<AirportDto>();
		for (Airport a : airportRepository.findAll()) {
			AirportDto d = new AirportDto();
			d.id = a.getId();
			d.name = a.getName();
			d.code = a.getCode();
			out.add(d);
		}
		return out;
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

    public AirportDto getAirportDtoById(long id) {
        var maybe = airportRepository.findById(id).orElse(null);
        if (maybe == null) return null;
        AirportDto d = new AirportDto();
        d.id = maybe.getId();
        d.name = maybe.getName();
        d.code = maybe.getCode();
        return d;
    }
}
