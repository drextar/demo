package br.com.fullfielment.dto;

import lombok.Data;

@Data
public class FreightRequestDto {
    private String postalCode;
    private Double weight;
    // Outros dados necessários para simulação de frete conforme espec. da VTEX
}
