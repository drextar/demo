package br.com.fullfielment.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Mock do Provider externo.
// Ajuste conforme necessário para simular retorno de preço e estoque.
@FeignClient(name = "freightProviderClient", url = "http://localhost:8081")
public interface FreightProviderClient {

    @GetMapping("/calculate")
    ProviderResponse getPriceAndStock(@RequestParam("sku") String sku,
                                      @RequestParam("quantity") Integer quantity,
                                      @RequestParam(value="postalCode", required=false) String postalCode,
                                      @RequestParam(value="country", required=false) String country);

    class ProviderResponse {
        private String sku;
        private Integer availableStock; // estoque disponível
        private Integer price;          // preço em centavos
        private Integer deliveryPrice;  // preço do frete em centavos (se postalCode e country informados)
        private Integer deliveryTimeDays; // dias de entrega estimados (se aplicável)

        // getters & setters
        public String getSku() {return sku;}
        public void setSku(String sku) {this.sku = sku;}
        public Integer getAvailableStock() {return availableStock;}
        public void setAvailableStock(Integer availableStock) {this.availableStock = availableStock;}
        public Integer getPrice() {return price;}
        public void setPrice(Integer price) {this.price = price;}
        public Integer getDeliveryPrice() {return deliveryPrice;}
        public void setDeliveryPrice(Integer deliveryPrice) {this.deliveryPrice = deliveryPrice;}
        public Integer getDeliveryTimeDays() {return deliveryTimeDays;}
        public void setDeliveryTimeDays(Integer deliveryTimeDays) {this.deliveryTimeDays = deliveryTimeDays;}
    }
}