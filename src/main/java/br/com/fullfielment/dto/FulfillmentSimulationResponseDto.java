package br.com.fullfielment.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

/**
 * Resposta conforme a documentação da VTEX.
 * A resposta deve conter `items` com os preços e disponibilidades,
 * e opcionalmente `logisticsInfo` se for cenário de checkout com postalCode e country.
 */
@Data
@Builder
public class FulfillmentSimulationResponseDto {
    private List<ResponseItem> items;
    private List<LogisticsInfo> logisticsInfo;

}
