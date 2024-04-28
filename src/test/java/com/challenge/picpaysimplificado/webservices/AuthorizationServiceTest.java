package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.webservices.authorizationservice.AuthorizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AuthorizationService authorizationService;

    String url;

    User payer;

    @BeforeEach
    void init() {
        url = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";
        this.authorizationService.setUrl(url);
        payer = new User(1L, "any", "any", "12345678909", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);
    }


    @Test
    void mustReceiverAResponseOfTheServiceIfEverythingOk() {
        ResponseEntity<Map> response = new ResponseEntity<>(Map.of("message", "Autorizado"), HttpStatus.OK);
        Mockito.when(this.restTemplate.getForEntity(url, Map.class)).thenReturn(response);

        assertDoesNotThrow(() -> this.authorizationService.verifyAuthorization(payer));

        Mockito.verify(this.restTemplate).getForEntity(url, Map.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);

    }

    @Test
    void mustFailedIfAuthorizationServiceReturnStatusCodeDifferentThe200() {
        ResponseEntity<Map> response = new ResponseEntity<>(Map.of("message", "Autorizado"), HttpStatus.BAD_REQUEST);
        Mockito.when(this.restTemplate.getForEntity(url, Map.class)).thenReturn(response);

        assertThrows(TransactionException.class, () -> this.authorizationService.verifyAuthorization(payer),
                "Transaction denied");

        Mockito.verify(this.restTemplate).getForEntity(url, Map.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);
    }

    @Test
    void mustFailedIfAuthorizationServiceReturnResponseBodyDifferentTheAutorizado() {
        ResponseEntity<Map> response = new ResponseEntity<>(Map.of("message", "Nao Autorizado"), HttpStatus.OK);
        Mockito.when(this.restTemplate.getForEntity(url, Map.class)).thenReturn(response);

        assertThrows(TransactionException.class, () -> this.authorizationService.verifyAuthorization(payer),
                "Transaction denied");

        Mockito.verify(this.restTemplate).getForEntity(url, Map.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);
    }
}

