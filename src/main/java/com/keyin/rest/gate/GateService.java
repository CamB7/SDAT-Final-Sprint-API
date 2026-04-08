package com.keyin.rest.gate;

import com.keyin.rest.gate.dto.GateDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GateService {
    private final GateRepository gateRepository;

    public GateService(GateRepository gateRepository) {
        this.gateRepository = gateRepository;
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
}
