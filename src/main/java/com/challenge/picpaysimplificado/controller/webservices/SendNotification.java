package com.challenge.picpaysimplificado.controller.webservices;

import com.challenge.picpaysimplificado.exceptions.WebServiceException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Setter
public class SendNotification {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${email.service.url}")
    private String url;

    public void send(){
        try{
            this.restTemplate.getForEntity(new URI(url), String.class);
        }
        catch (URISyntaxException | WebServiceException e){
            throw new WebServiceException(e.getMessage());
        }
    }

}
