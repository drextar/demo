package br.com.fullfielment.dto;

import lombok.Data;
import java.util.List;

/**
 * Representa o payload que a VTEX envia. Pode ser o cenário de indexing (sem postalCode/country)
 * ou checkout (com postalCode e country).
 */

@Data
public class FulfillmentSimulationRequestDto {
    private List<Item> items;
    private String postalCode; // opcional
    private String country;    // opcional
    private Boolean isCheckedIn; // opcional, cenário indexing

}

