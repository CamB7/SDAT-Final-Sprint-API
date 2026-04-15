package com.keyin.rest.gate;

import com.keyin.rest.gate.dto.GateDto;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/gates")
	public List<GateDto> getAllGates() {
		// You will need to create this method in your GateService if it doesn't exist:
		// return gateRepository.findAll().stream().map(this::toDto).collect(Collectors.toList());
		return gateService.getAllGateDtos();
	}

    @PutMapping("/gates/{id}")
    public GateDto updateGate(@PathVariable Long id, @RequestBody GateDto dto) {
        GateDto updated = gateService.updateGate(id, dto);
        if (updated == null) {
            throw new IllegalArgumentException("Gate not found with id: " + id);
        }
        return updated;
    }
}
