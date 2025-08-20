package br.com.fiap.visionhive;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "VisionHive API", version = "v2", description = "API do SaaS VisionHive")
)
@EnableCaching

public class VisionHiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionHiveApplication.class, args);
	}

}
