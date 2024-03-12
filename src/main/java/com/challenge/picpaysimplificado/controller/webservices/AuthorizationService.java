package com.challenge.picpaysimplificado.controller.webservices;

import com.challenge.picpaysimplificado.exceptions.WebServiceException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Setter
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${authorization.service.url}")
    private String url;

    public HttpStatusCode verifyAuthorization(){
        try{
            ResponseEntity<String> entity = this.restTemplate.getForEntity(new URI(url), String.class);
            return entity.getStatusCode();
        }
        catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        catch(WebServiceException e){
            throw new WebServiceException("Service unavailable");
        }
    }
}
