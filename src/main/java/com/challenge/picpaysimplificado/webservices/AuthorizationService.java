package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Setter
public class AuthorizationService {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${authorization.service.url}")
    private String url;

    public HttpStatusCode verifyAuthorization() {
        try {
            ResponseEntity<String> entity = this.restTemplate.getForEntity(url, String.class);
            return entity.getStatusCode();
        } catch (RestClientException e) {
            throw new WebServiceException("Authorization service is unavailable");
        }

    }
}
