package com.challenge.picpaysimplificado.springdoc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InfoConfig {

    @Bean
    public OpenAPI info() {
        return new OpenAPI().specVersion(SpecVersion.V31)
                .info(new Info()
                        .title("Challenge Picpay Simplificado")
                        .description("This is a simple service REST of transactions from common user to merchant, " +
                                "and from user common user to common user. The documentation was make through of the " +
                                "OpenAPI 3.0 Specification, more information's can be see in " +
                                "[OpenAPI Specification](https://swagger.io/resources/open-api/)")
                        .license(new License().name("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html"))
                        .version("0.0.1"))
                .servers(List.of(new Server()
                        .url("http://localhost:8080")));

    }


}
