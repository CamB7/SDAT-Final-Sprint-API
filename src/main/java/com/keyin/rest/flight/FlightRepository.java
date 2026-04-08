package com.keyin.rest.flight;

import com.keyin.rest.airport.Airport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByAircraftId(Long aircraftId);

    List<Flight> findByAirportTakeOffIdAndStatus(Long airportId, FlightStatus status);

    List<Flight> findByAirportLandingIdAndStatus(Long airportId, FlightStatus status);

    List<Flight> findByStatus(FlightStatus status);

    List<Flight> findByAirportTakeOffIdAndScheduledDepartureBetween(Long airportId, LocalDateTime from, LocalDateTime to);

    @Query("select distinct f.airportTakeOff from Flight f where f.aircraft.id = :aircraftId and f.airportTakeOff is not null")
    List<Airport> findDistinctTakeoffAirportsByAircraftId(@Param("aircraftId") Long aircraftId);

    @Query("select distinct f.airportLanding from Flight f where f.aircraft.id = :aircraftId and f.airportLanding is not null")
    List<Airport> findDistinctLandingAirportsByAircraftId(@Param("aircraftId") Long aircraftId);

    // Gate occupancy checks
    List<Flight> findByDepartureGateIdAndStatusIn(Long gateId, List<FlightStatus> statuses);
    List<Flight> findByArrivalGateIdAndStatusIn(Long gateId, List<FlightStatus> statuses);
}
