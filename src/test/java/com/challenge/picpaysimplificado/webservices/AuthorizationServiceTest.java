package com.challenge.picpaysimplificado.webservices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    AuthorizationService authorizationService;

    String url;

    @BeforeEach
    void init() {
        url = "https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc";
        this.authorizationService.setUrl(url);
    }


    @Test
    void doesNotShouldThrowNothingExceptionIfBeEverythingOK() {;
        Mockito.lenient().when(this.restTemplate.getForEntity(url, String.class)).thenReturn(
                        new RestTemplate().getForEntity(url, String.class));

        assertAll(
                () -> assertDoesNotThrow(() -> this.authorizationService.verifyAuthorization()),
                () -> assertEquals(this.authorizationService.verifyAuthorization(), HttpStatus.OK)
        );
        Mockito.verify(this.restTemplate, Mockito.times(2)).getForEntity(url, String.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);

    }

}