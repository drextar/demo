package br.com.fullfielment.controller;

import br.com.fullfielment.dto.FreightRequestDto;
import br.com.fullfielment.dto.FreightResponseDto;
import br.com.fullfielment.service.FreightSimulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pvt/orderForms")
@RequiredArgsConstructor
public class FreightSimulationController {

    private final FreightSimulationService freightSimulationService;

    @PostMapping(value = "/simulation", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<FreightResponseDto> simulate(@RequestBody FreightRequestDto request) {
        return freightSimulationService.simulateFreight(request);
    }
}
