package com.keyin.rest.gate;

import com.keyin.rest.gate.dto.GateDto;
import com.keyin.rest.airport.AirportRepository;
import com.keyin.rest.airport.Airport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class GateService {
    private final GateRepository gateRepository;
    private final AirportRepository airportRepository;

    public GateService(GateRepository gateRepository, AirportRepository airportRepository) {
        this.gateRepository = gateRepository;
        this.airportRepository = airportRepository;
    }

    public Optional<Gate> getGateById(Long id) {
        return gateRepository.findById(id);
    }

    public List<Gate> getGatesByAirportId(Long airportId) {
        return gateRepository.findByAirportId(airportId);
    }

    public GateDto getGateDtoById(Long id) {
        var g = gateRepository.findById(id).orElse(null);
        if (g == null) return null;
        GateDto d = new GateDto();
        d.id = g.getId();
        d.code = g.getCode();
        d.terminal = g.getTerminal();
        d.airportId = g.getAirport() != null ? g.getAirport().getId() : null;
        return d;
    }

    public List<GateDto> getGatesDtoByAirportId(Long airportId) {
        var list = new ArrayList<GateDto>();
        for (Gate g : gateRepository.findByAirportId(airportId)) {
            GateDto d = new GateDto();
            d.id = g.getId();
            d.code = g.getCode();
            d.terminal = g.getTerminal();
            d.airportId = g.getAirport() != null ? g.getAirport().getId() : null;
            list.add(d);
        }
        return list;
    }

    // New: update an existing gate
    public GateDto updateGate(Long id, GateDto dto) {
        var existing = gateRepository.findById(id).orElse(null);
        if (existing == null) return null;

        if (dto.code != null) existing.setCode(dto.code);
        if (dto.terminal != null) existing.setTerminal(dto.terminal);
        if (dto.airportId != null) {
            Airport a = airportRepository.findById(dto.airportId).orElse(null);
            existing.setAirport(a);
        }

        Gate saved = gateRepository.save(existing);
        GateDto out = new GateDto();
        out.id = saved.getId();
        out.code = saved.getCode();
        out.terminal = saved.getTerminal();
        out.airportId = saved.getAirport() != null ? saved.getAirport().getId() : null;
        return out;
    }

    // Return all gates as DTOs
    public List<GateDto> getAllGateDtos() {
        return StreamSupport.stream(gateRepository.findAll().spliterator(), false)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // Helper mapping method
    private GateDto toDto(Gate g) {
        if (g == null) return null;
        GateDto d = new GateDto();
        d.id = g.getId();
        d.code = g.getCode();
        d.terminal = g.getTerminal();
        d.airportId = g.getAirport() != null ? g.getAirport().getId() : null;
        return d;
    }
}
