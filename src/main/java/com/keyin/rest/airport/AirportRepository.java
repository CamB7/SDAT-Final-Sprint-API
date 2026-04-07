package com.keyin.rest.airport;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends CrudRepository<Airport, Long> {
    List<Airport> findByName(String name);
    Optional<Airport> findByCode(String code);
}
