package com.keyin.rest.flight;

import com.keyin.rest.aircraft.Aircraft;
import com.keyin.rest.airport.Airport;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

	public Flight(Aircraft aircraft, Airport airportTakeOff, Airport airportLanding) {
		this.aircraft = aircraft;
		this.airportTakeOff = airportTakeOff;
		this.airportLanding = airportLanding;

		if (aircraft != null) {
			aircraft.addAirportTakeoff(airportTakeOff);
			aircraft.addAirportLanding(airportLanding);
		}
	}

}
