package com.keyin.rest.flight.dto;

import com.keyin.rest.flight.FlightStatus;

import java.time.LocalDateTime;

public class FlightDto {
    public Long id;
    public Long aircraftId;
	public String airlineName;
    public FlightStatus status;
    public LocalDateTime scheduledDeparture;
    public LocalDateTime actualDeparture;
    public Long departureAirportId;
    public String departureAirportCode;
    public String departureGateCode;
    public LocalDateTime scheduledArrival;
    public LocalDateTime actualArrival;
    public Long arrivalAirportId;
    public String arrivalAirportCode;
    public String arrivalGateCode;

    public FlightDto() {}
}
