package br.com.fullfielment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LogisticsInfo {
    private Integer itemIndex;
    private Integer stockBalance;
    private List<DeliveryOption> deliveryOptions;
}
