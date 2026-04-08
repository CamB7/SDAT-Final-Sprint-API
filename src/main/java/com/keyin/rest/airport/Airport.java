package com.keyin.rest.airport;

import com.keyin.rest.flight.Flight;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
@ToString(exclude = "flightsTakeOff,flightsLanding")
public class Airport {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String code;

	@OneToMany(mappedBy = "airportTakeOff", cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonIgnore
	private List<Flight> flightsTakeOff = new ArrayList<>();

	@OneToMany(mappedBy = "airportLanding", cascade = CascadeType.ALL, orphanRemoval = false)
	@JsonIgnore
	private List<Flight> flightsLanding = new ArrayList<>();

}
