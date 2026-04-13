package com.keyin.rest.aircraft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.rest.airport.Airport;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Aircraft {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String type;
	private String airlineName;
	private Integer numberOfSeats;

	@ManyToMany
	@JoinTable(
			name = "aircraft_airport_takeoff",
			joinColumns = @JoinColumn(name = "aircraft_id"),
			inverseJoinColumns = @JoinColumn(name = "airport_id")
	)
	@JsonIgnore
	private List<Airport> airportsTakeoff = new ArrayList<>();

	@ManyToMany
	@JoinTable(
			name = "aircraft_airport_landing",
			joinColumns = @JoinColumn(name = "aircraft_id"),
			inverseJoinColumns = @JoinColumn(name = "airport_id")
	)
	@JsonIgnore
	private List<Airport> airportsLanding = new ArrayList<>();

	public Aircraft(String type, String airlineName, int numberOfSeats) {
		this.type = type;
		this.airlineName = airlineName;
		this.numberOfSeats = numberOfSeats;
	}

	public void addAirportTakeoff(Airport airport) {
		if (airport != null) {
			airportsTakeoff.add(airport);
		}
	}

	public void addAirportLanding(Airport airport) {
		if (airport != null) {
			airportsLanding.add(airport);
		}
	}

	public String getAirline() {
		return airlineName != null ? airlineName : "";
	}
}
