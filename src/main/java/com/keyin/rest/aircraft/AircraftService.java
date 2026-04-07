package com.keyin.rest.aircraft;

import com.keyin.rest.airport.Airport;
import com.keyin.rest.flight.Flight;
import com.keyin.rest.flight.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

@Service
public class AircraftService {

    @Autowired
    private AircraftRepository aircraftRepository;

    @Autowired
    private FlightRepository flightRepository;

    public Iterable<Aircraft> getAllAircrafts() {
        return aircraftRepository.findAll();
    }

    public Optional<Aircraft> getAircraftById(long id) {
        return Optional.ofNullable(aircraftRepository.findById(id).orElse(null));
    }

    public Aircraft createAircraft(Aircraft aircraft) {
        return aircraftRepository.save(aircraft);
    }

    public void deleteAircraftById(long id) {
        Aircraft aircraftFound = aircraftRepository.findById(id).orElse(null);
        if (aircraftFound != null) {
            aircraftRepository.delete(aircraftFound);
        }
    }

    public Aircraft updateAircraft(Long id, Aircraft aircraft) {
        Aircraft aircraftToUpdate = aircraftRepository.findById(id).orElse(null);
        if (aircraftToUpdate == null) {
            throw new IllegalArgumentException("Aircraft not found: " + id);
        }

        if (aircraft.getType() != null) aircraftToUpdate.setType(aircraft.getType());
        if (aircraft.getAirlineName() != null) aircraftToUpdate.setAirlineName(aircraft.getAirlineName());
        if (aircraft.getNumberOfSeats() != null) aircraftToUpdate.setNumberOfSeats(aircraft.getNumberOfSeats());
        if (aircraft.getAirportsTakeoff() != null) aircraftToUpdate.setAirportsTakeoff(aircraft.getAirportsTakeoff());
        if (aircraft.getAirportsLanding() != null) aircraftToUpdate.setAirportsLanding(aircraft.getAirportsLanding());

        return aircraftRepository.save(aircraftToUpdate);
    }

    @Transactional(readOnly = true)
    public List<Airport> getAirportsForAircraft(Long aircraftId) {
        if (aircraftId == null) return List.of();
        if (!aircraftRepository.existsById(aircraftId)) return List.of();

        List<Airport> takeoffs = flightRepository.findDistinctTakeoffAirportsByAircraftId(aircraftId);
        List<Airport> landings = flightRepository.findDistinctLandingAirportsByAircraftId(aircraftId);

        LinkedHashSet<Airport> set = new LinkedHashSet<>();
        if (takeoffs != null && !takeoffs.isEmpty()) set.addAll(takeoffs);
        if (landings != null && !landings.isEmpty()) set.addAll(landings);

        if (set.isEmpty()) {
            List<Flight> flights = flightRepository.findByAircraftId(aircraftId);
            if (flights != null) {
                for (Flight f : flights) {
                    if (f == null) continue;
                    if (f.getAirportTakeOff() != null) set.add(f.getAirportTakeOff());
                    if (f.getAirportLanding() != null) set.add(f.getAirportLanding());
                }
            }
        }
        return new ArrayList<>(set);
    }

    @Transactional(readOnly = true)
    public List<Airport> getTakeoffAirportsForAircraft(Long aircraftId) {
        if (aircraftId == null) return List.of();
        if (!aircraftRepository.existsById(aircraftId)) return List.of();
        List<Airport> takeoffs = flightRepository.findDistinctTakeoffAirportsByAircraftId(aircraftId);
        return takeoffs == null ? List.of() : takeoffs;
    }

    @Transactional(readOnly = true)
    public List<Airport> getLandingAirportsForAircraft(Long aircraftId) {
        if (aircraftId == null) return List.of();
        if (!aircraftRepository.existsById(aircraftId)) return List.of();
        List<Airport> landings = flightRepository.findDistinctLandingAirportsByAircraftId(aircraftId);
        return landings == null ? List.of() : landings;
    }

    @Transactional(readOnly = true)
    public List<Flight> getFlightsForAircraft(Long aircraftId) {
        if (aircraftId == null) return List.of();
        if (!aircraftRepository.existsById(aircraftId)) return List.of();
        List<Flight> flights = flightRepository.findByAircraftId(aircraftId);
        return flights == null ? List.of() : flights;
    }
}
