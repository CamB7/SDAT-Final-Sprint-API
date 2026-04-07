package com.keyin.rest.flight;

import com.keyin.rest.aircraft.Aircraft;
import com.keyin.rest.airport.Airport;
import jakarta.persistence.*;

@Entity
public class Flight {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Aircraft aircraft;

	@ManyToOne
	private Airport airportTakeOff;

	@ManyToOne
	private Airport airportLanding;

	public Flight() {
	}

	public Flight(Aircraft aircraft, Airport airportTakeOff, Airport airportLanding) {
		this.aircraft = aircraft;
		this.airportTakeOff = airportTakeOff;
		this.airportLanding = airportLanding;

		if (aircraft != null) {
			aircraft.addAirportTakeoff(airportTakeOff);
			aircraft.addAirportLanding(airportLanding);
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Aircraft getAircraft() {
		return aircraft;
	}

	public void setAircraft(Aircraft aircraft) {
		this.aircraft = aircraft;
	}

	public Airport getAirportTakeOff() {
		return airportTakeOff;
	}

	public void setAirportTakeOff(Airport airportTakeOff) {
		this.airportTakeOff = airportTakeOff;
	}

	public Airport getAirportLanding() {
		return airportLanding;
	}

	public void setAirportLanding(Airport airportLanding) {
		this.airportLanding = airportLanding;
	}

	@Override
	public String toString() {
		return "Flight{" +
				"id=" + id +
				", aircraft=" + aircraft +
				", airportTakeOff=" + airportTakeOff +
				", airportLanding=" + airportLanding +
				'}';
	}
}
