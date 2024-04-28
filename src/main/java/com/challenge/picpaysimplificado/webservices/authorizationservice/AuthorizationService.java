package com.challenge.picpaysimplificado.webservices.authorizationservice;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
import com.challenge.picpaysimplificado.webservices.authorizationservice.core.AuthorizationServiceUseCase;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@Setter
public class AuthorizationService implements AuthorizationServiceUseCase {

    @Autowired
    private RestTemplate restTemplate;

    @Value(value = "${authorization.service.url}")
    private String url;

    public void verifyAuthorization(User payer) {
        ResponseEntity<Map> authorizationResponse;
        try {
            authorizationResponse = this.restTemplate.getForEntity(url, Map.class);
        } catch (RestClientException e) {
            throw new WebServiceException("Authorization service is unavailable");
        }
        if (authorizationResponse.getStatusCode() != HttpStatus.OK ||
                !("Autorizado".equalsIgnoreCase((String) Objects.requireNonNull(authorizationResponse.getBody()).get("message")))) {
            throw new TransactionException("Transaction denied");

        }
    }

}

