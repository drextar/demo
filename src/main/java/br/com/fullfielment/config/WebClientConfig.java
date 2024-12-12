package br.com.fullfielment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Caso queira usar WebClient para chamadas reativas externas,
 * já deixamos configurado. Pode ser útil no futuro.
 */
@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
