package br.com.fullfielment.service;

import br.com.fullfielment.dto.FreightRequestDto;
import br.com.fullfielment.dto.FreightResponseDto;
import br.com.fullfielment.exception.CustomException;
import br.com.fullfielment.infrastructure.FreightProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightSimulationService {

    private final FreightProviderClient freightProviderClient;

    public Mono<FreightResponseDto> simulateFreight(FreightRequestDto request) {
        return Mono.fromCallable(() -> {
                    FreightProviderClient.FreightProviderResponse resp = freightProviderClient.getFreightOptions(request.getPostalCode(), request.getWeight());

                    // Regra de negócio: se frete >= 10000, lança exceção
                    if (resp.getPrice() != null && resp.getPrice() >= 10000) {
                        throw new CustomException("O valor do frete é muito alto!");
                    }

                    // Converter provider response para FreightResponseDto
                    FreightResponseDto.ShippingOption option = FreightResponseDto.ShippingOption.builder()
                            .id(resp.getId())
                            .name(resp.getName())
                            .price(resp.getPrice())
                            .deliveryTime(resp.getDeliveryTime())
                            .build();

                    return FreightResponseDto.builder()
                            .shippingOptions(List.of(option))
                            .build();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(java.time.Duration.ofMillis(1500))
                .onErrorResume(e -> {
                    if (e instanceof CustomException) {
                        return Mono.error(e);
                    }
                    FreightResponseDto fallback = FreightResponseDto.builder()
                            .shippingOptions(List.of())
                            .build();
                    return Mono.just(fallback);
                });
    }
}
