package com.keyin.rest.aircraft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.keyin.rest.airport.Airport;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
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

	public Aircraft() {
	}

	public Aircraft(String type, String airlineName, int numberOfSeats) {
		this.type = type;
		this.airlineName = airlineName;
		this.numberOfSeats = numberOfSeats;
	}

    public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {return type;}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setNumberOfSeats(Integer numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public Integer getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setAirportsTakeoff(List<Airport> airportsTakeoff) {
		this.airportsTakeoff = airportsTakeoff;
	}

	public void addAirportTakeoff(Airport airport) {
		if (airport != null) {
			airportsTakeoff.add(airport);
		}
	}

	public List<Airport> getAirportsTakeoff() {
		return airportsTakeoff;
	}

	public void setAirportsLanding(List<Airport> airportsLanding) {
		this.airportsLanding = airportsLanding;
	}

	public void addAirportLanding(Airport airport) {
		if (airport != null) {
			airportsLanding.add(airport);
		}
	}

	public List<Airport> getAirportsLanding() {
		return airportsLanding;
	}

	@Override
	public String toString() {
		return "Aircraft{" +
				"id=" + id +
				", type=" + type +
				", airlineName=" + airlineName +
				", numberOfSeats=" + numberOfSeats +
				", airportsTakeoff=" + airportsTakeoff +
				", airportsLanding=" + airportsLanding +
				'}';
	}
}
