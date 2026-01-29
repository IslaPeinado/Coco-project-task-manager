package com.coco.config;

import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.coco")
                .pathsToMatch("/api/**")
                .build();
    }

    @Bean
    public Info apiInfo() {
        return new Info().title("COCO API").description("Documentation for the COCO Project API");
    }
}
