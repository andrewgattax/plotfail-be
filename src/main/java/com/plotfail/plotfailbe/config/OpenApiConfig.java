package com.plotfail.plotfailbe.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configurazione OpenAPI/Swagger per documentare le API e il meccanismo di sicurezza Bearer JWT.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI uniconnectOpenAPI() {
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Uniconnect API")
                                .version("v1")
                                .description("Documentazione delle API di Uniconnect"))
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT"))
                );
    }
}
