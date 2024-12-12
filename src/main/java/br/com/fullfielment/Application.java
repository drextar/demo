package br.com.fullfielment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableFeignClients(basePackages = "br.com.fullfielment.infrastructure")
public class Application {
	public static void main(String[] args) {
		// Para ver stacktrace completo em caso de debug reativo (opcional)
		Hooks.onOperatorDebug();
		SpringApplication.run(Application.class, args);
	}
}