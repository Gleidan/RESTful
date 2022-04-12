package org.school21.restful.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("To be RESTful")
                        .version("1.0.0")
                        .contact(new Contact()
                                .email("jconcent@student.21-school.ru")
                                .name("Jconcent, Sesprigg")));
    }
}
