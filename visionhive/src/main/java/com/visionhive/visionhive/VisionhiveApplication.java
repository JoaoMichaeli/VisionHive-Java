package com.visionhive.visionhive;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(title = "VisionHive API", version = "v1", description = "API do SaaS VisionHive")
)
@EnableCaching

public class VisionhiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(VisionhiveApplication.class, args);
	}

}
