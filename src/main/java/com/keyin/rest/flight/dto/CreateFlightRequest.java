package com.keyin.rest.flight.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CreateFlightRequest {

	@NotNull
	private Long aircraftId;

	private String flightNumber;
	private String airlineName;

	// Change these from Long IDs to String Codes
	private String departureAirportCode;
	private String arrivalAirportCode;

	@NotNull
	private LocalDateTime scheduledDeparture;

	@NotNull
	private LocalDateTime scheduledArrival;

	private String departureGateCode;
	private String arrivalGateCode;
	private String status;

	public CreateFlightRequest() {}

	// --- Add Getters & Setters for everything ---

	public Long getAircraftId() { return aircraftId; }
	public void setAircraftId(Long aircraftId) { this.aircraftId = aircraftId; }

	public String getFlightNumber() { return flightNumber; }
	public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

	public String getAirlineName() { return airlineName; }
	public void setAirlineName(String airlineName) { this.airlineName = airlineName; }

	public String getDepartureAirportCode() { return departureAirportCode; }
	public void setDepartureAirportCode(String departureAirportCode) { this.departureAirportCode = departureAirportCode; }

	public String getArrivalAirportCode() { return arrivalAirportCode; }
	public void setArrivalAirportCode(String arrivalAirportCode) { this.arrivalAirportCode = arrivalAirportCode; }

	public LocalDateTime getScheduledDeparture() { return scheduledDeparture; }
	public void setScheduledDeparture(LocalDateTime scheduledDeparture) { this.scheduledDeparture = scheduledDeparture; }

	public LocalDateTime getScheduledArrival() { return scheduledArrival; }
	public void setScheduledArrival(LocalDateTime scheduledArrival) { this.scheduledArrival = scheduledArrival; }

	public String getDepartureGateCode() { return departureGateCode; }
	public void setDepartureGateCode(String departureGateCode) { this.departureGateCode = departureGateCode; }

	public String getArrivalGateCode() { return arrivalGateCode; }
	public void setArrivalGateCode(String arrivalGateCode) { this.arrivalGateCode = arrivalGateCode; }

	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}