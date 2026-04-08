package com.keyin.rest.gate;

import com.keyin.rest.gate.dto.GateDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GateController {
    private final GateService gateService;

    public GateController(GateService gateService) {
        this.gateService = gateService;
    }

    @GetMapping("/gates/{id}")
    public GateDto getGate(@PathVariable Long id) {
        return gateService.getGateDtoById(id);
    }

    @GetMapping("/airports/{airportId}/gates")
    public List<GateDto> getGatesForAirport(@PathVariable Long airportId) {
        return gateService.getGatesDtoByAirportId(airportId);
    }
}
