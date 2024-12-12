package br.com.fullfielment.infrastructure;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Exemplo de client Feign para um serviço externo de frete.
 * Ajustar URL, path e parâmetros conforme necessário.
 */

@FeignClient(name = "freightProviderClient", url = "https://freight-provider.com/api", configuration = FreightProviderConfig.class)
public interface FreightProviderClient {

    @GetMapping("/calculate")
    FreightProviderResponse getFreightOptions(@RequestParam("postalCode") String postalCode,
                                              @RequestParam("weight") Double weight);

    // Classe interna ou externa representando a resposta do provider
    class FreightProviderResponse {
        // Defina os campos conforme a resposta do provider
        private String id;
        private String name;
        private Double price;
        private Integer deliveryTime;

        // getters e setters
        public String getId() { return id; }
        public void setId(String id) {this.id = id;}
        public String getName() {return name;}
        public void setName(String name) {this.name = name;}
        public Double getPrice() {return price;}
        public void setPrice(Double price) {this.price = price;}
        public Integer getDeliveryTime() {return deliveryTime;}
        public void setDeliveryTime(Integer deliveryTime) {this.deliveryTime = deliveryTime;}
    }
}