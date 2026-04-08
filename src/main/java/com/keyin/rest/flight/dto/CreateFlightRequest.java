package com.keyin.rest.flight.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class CreateFlightRequest {

    @NotNull
    private Long aircraftId;

    private Long airportTakeOffId;
    private Long airportLandingId;

    @NotNull
    private LocalDateTime scheduledDeparture;

    @NotNull
    private LocalDateTime scheduledArrival;

    public CreateFlightRequest() {}

    public Long getAircraftId() { return aircraftId; }
    public void setAircraftId(Long aircraftId) { this.aircraftId = aircraftId; }

    public Long getAirportTakeOffId() { return airportTakeOffId; }
    public void setAirportTakeOffId(Long airportTakeOffId) { this.airportTakeOffId = airportTakeOffId; }

    public Long getAirportLandingId() { return airportLandingId; }
    public void setAirportLandingId(Long airportLandingId) { this.airportLandingId = airportLandingId; }

    public LocalDateTime getScheduledDeparture() { return scheduledDeparture; }
    public void setScheduledDeparture(LocalDateTime scheduledDeparture) { this.scheduledDeparture = scheduledDeparture; }

    public LocalDateTime getScheduledArrival() { return scheduledArrival; }
    public void setScheduledArrival(LocalDateTime scheduledArrival) { this.scheduledArrival = scheduledArrival; }
}
