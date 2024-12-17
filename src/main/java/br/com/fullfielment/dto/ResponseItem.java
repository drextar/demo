package br.com.fullfielment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ResponseItem {
    private String id;
    private String availability; // "available" ou "unavailable"
    private String seller;
    private Integer quantity;
    private Integer price;       // em centavos
    private Integer listPrice;   // em centavos
    private Integer sellingPrice; // em centavos
    private String measurementUnit;
    private Integer unitMultiplier;
    private List<Object> attachmentOfferings;
    private List<Object> offerings;
    private List<Object> priceTags;
}
