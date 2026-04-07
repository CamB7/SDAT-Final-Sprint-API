package com.keyin.rest.flight;

import com.keyin.rest.flight.Flight;
import com.keyin.rest.airport.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    @Query("select f from Flight f where f.aircraft.id = :aircraftId")
    List<Flight> findByAircraftId(@Param("aircraftId") Long aircraftId);

    @Query("select distinct f.airportTakeOff from Flight f where f.aircraft.id = :aircraftId and f.airportTakeOff is not null")
    List<Airport> findDistinctTakeoffAirportsByAircraftId(@Param("aircraftId") Long aircraftId);

    @Query("select distinct f.airportLanding from Flight f where f.aircraft.id = :aircraftId and f.airportLanding is not null")
    List<Airport> findDistinctLandingAirportsByAircraftId(@Param("aircraftId") Long aircraftId);
}
