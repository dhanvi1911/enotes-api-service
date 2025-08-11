package com.dhanvi.enotes_api_service.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI (){
        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        info.setTitle("Enotes Application");
        info.setDescription("Enotes API");
        info.setVersion("1.0.0");
        info.setContact(new Contact().email("dhanvibhanushali@gmail.com").name("Dhanvi Mange"));

        openAPI.setInfo(info);

        SecurityScheme securityScheme = new SecurityScheme().name("Authorization")
                .scheme("bearer").type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT").in(SecurityScheme.In.HEADER);

        Components components = new Components().addSecuritySchemes("Token",securityScheme);
        openAPI.setComponents(components);
        openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));
        return openAPI;
    }
}
