package com.challenge.picpaysimplificado.webservices.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebServiceBean {

    @Bean
    public RestTemplate restTemplateBean(){
        return new RestTemplate();
    }
}
