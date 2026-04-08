package com.keyin.rest.gate;

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
public class Gate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // e.g., A12
    private String terminal;

    @ManyToOne(fetch = FetchType.LAZY)
    private Airport airport;
}
