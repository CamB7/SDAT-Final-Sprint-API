package com.keyin.rest.airport;

import com.keyin.rest.aircraft.Aircraft;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
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

}
