package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.webservices.SendNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SendNotificationTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SendNotification sendNotification;

    String url;

    @BeforeEach
    void init(){
        url = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";
        this.sendNotification.setUrl(url);
    }

    @Test
    void doesNotShouldThrowNothingExceptionIfBeEverythingOK() {
        Mockito.lenient().when(this.restTemplate.getForEntity(url, String.class)).thenReturn(
                new RestTemplate().getForEntity(url, String.class)
        );

        assertDoesNotThrow(() -> this.sendNotification.send());

    }
}