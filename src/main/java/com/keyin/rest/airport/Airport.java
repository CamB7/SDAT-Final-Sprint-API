package com.keyin.rest.airport;

import com.keyin.rest.aircraft.Aircraft;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@Entity
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String code;

	@ManyToMany(mappedBy = "airportsTakeoff")
	@JsonIgnore
	private List<Aircraft> aircraftTakeoff;

	@ManyToMany(mappedBy = "airportsLanding")
	@JsonIgnore
	private List<Aircraft> aircraftLanding;

	public Airport() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Aircraft> getAircraftLanding() {
		return aircraftLanding;
	}

	public void setAircraftLanding(List<Aircraft> aircraft) {
		this.aircraftLanding = aircraft;
	}

	public List<Aircraft> getAircraftTakeoff() {
		return aircraftTakeoff;
	}

	public void setAircraftTakeoff(List<Aircraft> aircraft) {
		this.aircraftTakeoff = aircraft;
	}
}
