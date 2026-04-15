package com.keyin.rest.gate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GateRepository extends CrudRepository<Gate, Long> {
    Optional<Gate> findByCode(String code);
    List<Gate> findByAirportId(Long airportId);
}
