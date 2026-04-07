package com.keyin.rest.aircraft;

import com.keyin.rest.airport.Airport;
import com.keyin.rest.flight.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aircrafts")
public class AircraftController {

    @Autowired
    private AircraftService aircraftService;

    @GetMapping
    public Iterable<Aircraft> getAllAircrafts() {
        return aircraftService.getAllAircrafts();
    }

    @GetMapping("/{id}")
    public Optional<Aircraft> getAircraftById(@PathVariable long id) {
        return aircraftService.getAircraftById(id);
    }

    @GetMapping("/{id}/airports")
    public ResponseEntity<List<Airport>> getAirportsForAircraft(@PathVariable long id) {
        Optional<Aircraft> maybe = aircraftService.getAircraftById(id);
        if (maybe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Airport> airports = aircraftService.getAirportsForAircraft(id);
        return ResponseEntity.ok(airports == null ? List.of() : airports);
    }

    @GetMapping("/{id}/airports/takeoff")
    public ResponseEntity<List<Airport>> getTakeoffAirportsForAircraft(@PathVariable long id) {
        Optional<Aircraft> maybe = aircraftService.getAircraftById(id);
        if (maybe.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(aircraftService.getTakeoffAirportsForAircraft(id));
    }

    @GetMapping("/{id}/airports/landing")
    public ResponseEntity<List<Airport>> getLandingAirportsForAircraft(@PathVariable long id) {
        Optional<Aircraft> maybe = aircraftService.getAircraftById(id);
        if (maybe.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(aircraftService.getLandingAirportsForAircraft(id));
    }

    @GetMapping("/{id}/flights")
    public ResponseEntity<List<Flight>> getFlightsForAircraft(@PathVariable long id) {
        Optional<Aircraft> maybe = aircraftService.getAircraftById(id);
        if (maybe.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Flight> flights = aircraftService.getFlightsForAircraft(id);
        return ResponseEntity.ok(flights == null ? List.of() : flights);
    }

    @PostMapping
    public Aircraft createAircraft(@RequestBody Aircraft aircraft) {
        return aircraftService.createAircraft(aircraft);
    }

    @DeleteMapping("/{id}")
    public void deleteAircraft(@PathVariable long id) {
        aircraftService.deleteAircraftById(id);
    }

    @PutMapping("/{id}")
    public Aircraft updateAircraft(@PathVariable Long id, @RequestBody Aircraft aircraft) {
        return aircraftService.updateAircraft(id, aircraft);
    }
}
