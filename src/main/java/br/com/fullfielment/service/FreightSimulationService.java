package br.com.fullfielment.service;

import br.com.fullfielment.dto.*;
import br.com.fullfielment.exception.CustomException;
import br.com.fullfielment.infrastructure.FreightProviderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FreightSimulationService {

    private final FreightProviderClient freightProviderClient;

    public Mono<FulfillmentSimulationResponseDto> simulate(FulfillmentSimulationRequestDto request) {

        // Determinar cenário:
        boolean hasAddress = request.getPostalCode() != null && request.getCountry() != null;

        return Mono.fromCallable(() -> {
                    List<ResponseItem> responseItems = new ArrayList<>();
                    List<LogisticsInfo> logisticsInfoList = null;

                    // Iterar sobre os itens e consultar o provider
                    for (int i = 0; i < request.getItems().size(); i++) {
                        Item item = request.getItems().get(i);
                        FreightProviderClient.ProviderResponse providerResp = freightProviderClient.getPriceAndStock(
                                item.getId(),
                                item.getQuantity(),
                                request.getPostalCode(),
                                request.getCountry()
                        );

                        if (providerResp.getPrice() != null && providerResp.getPrice() >= 10000) {
                            // Regra de negócio: se preço >= 10.000 centavos, lançar exceção
                            throw new CustomException("O valor do item " + item.getId() + " é muito alto!");
                        }

                        // Disponibilidade
                        String availability = (providerResp.getAvailableStock() != null && providerResp.getAvailableStock() > 0)
                                ? "available"
                                : "unavailable";

                        ResponseItem responseItem = ResponseItem.builder()
                                .id(item.getId())
                                .availability(availability)
                                .seller(item.getSeller())
                                .quantity(item.getQuantity())
                                .price(providerResp.getPrice() != null ? providerResp.getPrice() : 0)
                                .listPrice(providerResp.getPrice() != null ? providerResp.getPrice() : 0)
                                .sellingPrice(providerResp.getPrice() != null ? providerResp.getPrice() : 0)
                                .measurementUnit("un")
                                .unitMultiplier(1)
                                .attachmentOfferings(List.of())
                                .offerings(List.of())
                                .priceTags(List.of())
                                .build();

                        responseItems.add(responseItem);

                        if (hasAddress) {
                            // Cenário checkout: se postalCode/country informados, retorna logisticsInfo
                            Integer stockBalance = providerResp.getAvailableStock() != null ? providerResp.getAvailableStock() : 0;

                            // Se não houver postalCode/country no checkout scenario, interpretar como zero (já tratado acima)
                            // Mas aqui como temos hasAddress = true, teremos stock normal.

                            List<DeliveryOption> deliveryOptions = new ArrayList<>();
                            if (stockBalance > 0 && providerResp.getDeliveryPrice() != null && providerResp.getDeliveryTimeDays() != null) {
                                DeliveryOption delivery = DeliveryOption.builder()
                                        .id("Normal")
                                        .price(providerResp.getDeliveryPrice())
                                        .estimate(providerResp.getDeliveryTimeDays() + "bd") // por ex: "5bd"
                                        .build();
                                deliveryOptions.add(delivery);
                            }

                            if (logisticsInfoList == null) {
                                logisticsInfoList = new ArrayList<>();
                            }

                            LogisticsInfo li = LogisticsInfo.builder()
                                    .itemIndex(i)
                                    .stockBalance(stockBalance)
                                    .deliveryOptions(deliveryOptions)
                                    .build();

                            logisticsInfoList.add(li);
                        }
                    }

                    return FulfillmentSimulationResponseDto.builder()
                            .items(responseItems)
                            .logisticsInfo(hasAddress ? logisticsInfoList : null)
                            .build();
                })
                .subscribeOn(Schedulers.boundedElastic())
                .timeout(java.time.Duration.ofMillis(1500))
                .onErrorResume(e -> {
                    // Se for CustomException, deixa propagar para o handler.
                    if (e instanceof CustomException) {
                        return Mono.error(e);
                    }
                    // Se for outro erro (timeout, provider indisponível), podemos retornar fallback vazio
                    FulfillmentSimulationResponseDto fallback = FulfillmentSimulationResponseDto.builder()
                            .items(List.of())
                            .build();
                    return Mono.just(fallback);
                });
    }
}
