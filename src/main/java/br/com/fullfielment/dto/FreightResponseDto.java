package br.com.fullfielment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class FreightResponseDto {
    // Exemplo de retorno esperado (adaptar à especificação VTEX)
    // Na doc da VTEX existe um exemplo de payload de resposta.
    // Abaixo um exemplo fictício.
    private List<ShippingOption> shippingOptions;

    @Data
    @Builder
    public static class ShippingOption {
        private String id;
        private String name;
        private Double price;
        private Integer deliveryTime; // em dias ou horas
    }
}
