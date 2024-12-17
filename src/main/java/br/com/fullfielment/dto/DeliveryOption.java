package br.com.fullfielment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryOption {
    private String id;
    private Integer price;   // centavos
    private String estimate; // ex: "5bd" para 5 dias úteis
}
