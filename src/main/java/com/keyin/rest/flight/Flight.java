package com.keyin.rest.flight;

import com.keyin.rest.aircraft.Aircraft;
import com.keyin.rest.airport.Airport;
import com.keyin.rest.gate.Gate;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "aircraft")
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	Aircraft aircraft;

	@ManyToOne(fetch = FetchType.LAZY)
	Airport airportTakeOff;

	@ManyToOne(fetch = FetchType.LAZY)
	Gate departureGate;

	LocalDateTime scheduledDeparture;
	LocalDateTime actualDeparture;

	@ManyToOne(fetch = FetchType.LAZY)
	Airport airportLanding;

	@ManyToOne(fetch = FetchType.LAZY)
	Gate arrivalGate;

	LocalDateTime scheduledArrival;
	LocalDateTime actualArrival;

	@Enumerated(EnumType.STRING)
	FlightStatus status = FlightStatus.SCHEDULED;

	long lastUpdated;

	public Flight(Aircraft aircraft, Airport airportTakeOff, Airport airportLanding, LocalDateTime scheduledDeparture, LocalDateTime scheduledArrival) {
		this.aircraft = aircraft;
		this.airportTakeOff = airportTakeOff;
		this.airportLanding = airportLanding;
		this.scheduledDeparture = scheduledDeparture;
		this.scheduledArrival = scheduledArrival;
	}

	public void assignDepartureGate(Gate gate) {
		this.departureGate = gate;
	}

	public void assignArrivalGate(Gate gate) {
		this.arrivalGate = gate;
	}

	// Explicit accessors for static analysis (Lombok may not be processed by the checker)
	public Long getId() { return this.id; }
	public Aircraft getAircraft() { return this.aircraft; }
	public void setAircraft(Aircraft a) { this.aircraft = a; }
	public Airport getAirportTakeOff() { return this.airportTakeOff; }
	public void setAirportTakeOff(Airport a) { this.airportTakeOff = a; }
	public Gate getDepartureGate() { return this.departureGate; }
	public LocalDateTime getScheduledDeparture() { return this.scheduledDeparture; }
	public LocalDateTime getActualDeparture() { return this.actualDeparture; }
	public Airport getAirportLanding() { return this.airportLanding; }
	public void setAirportLanding(Airport a) { this.airportLanding = a; }
	public Gate getArrivalGate() { return this.arrivalGate; }
	public LocalDateTime getScheduledArrival() { return this.scheduledArrival; }
	public LocalDateTime getActualArrival() { return this.actualArrival; }
	public FlightStatus getStatus() { return this.status; }
	public void setStatus(FlightStatus s) { this.status = s; }
	public long getLastUpdated() { return this.lastUpdated; }
	public void setLastUpdated(long t) { this.lastUpdated = t; }
}
