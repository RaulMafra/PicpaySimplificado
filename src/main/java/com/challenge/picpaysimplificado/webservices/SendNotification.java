package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Setter
public class SendNotification {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${email.service.url}")
    private String url;

    public void send(){
        try{
            this.restTemplate.getForEntity(url, String.class);
        }
        catch (RestClientException e){
            throw new WebServiceException("Email service is unavailable");
        }
    }

}
