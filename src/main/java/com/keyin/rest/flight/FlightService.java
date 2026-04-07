package com.keyin.rest.flight;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FlightService {

	private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
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

		if (flight.getAircraft() != null) {
			flightToUpdate.setAircraft(flight.getAircraft());
		}

		if (flight.getAirportTakeOff() != null) {
			flightToUpdate.setAirportTakeOff(flight.getAirportTakeOff());
		}

		if (flight.getAirportLanding() != null) {
			flightToUpdate.setAirportLanding(flight.getAirportLanding());
		}

		return flightRepository.save(flightToUpdate);
	}

}
