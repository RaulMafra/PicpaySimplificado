package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.NotificationDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class SendNotificationTest {

    @Mock
    RestTemplate restTemplate;

    @InjectMocks
    SendNotification sendNotification;

    String url;

    User payer;

    NotificationDTO notificationDTO;

    @BeforeEach
    void init(){
        url = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";
        this.sendNotification.setUrl(url);

        payer = new User(1L, "any", "any", "12345678909", "test@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        notificationDTO = new NotificationDTO("test@email.com", "Pagamento realizado com sucesso");
    }

    @Test
    void doesNotShouldThrowNothingExceptionIfBeEverythingOK() {
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.OK);
        Mockito.when(this.restTemplate.postForEntity(url, notificationDTO, String.class)).thenReturn(response);

        assertDoesNotThrow(() -> this.sendNotification.send(payer, notificationDTO.message()));

        Mockito.verify(this.restTemplate).postForEntity(url, notificationDTO, String.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);

    }

    @Test
    void mustFailedIfStatusCodeReturnDifferentThe200(){
        ResponseEntity<String> response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Mockito.when(this.restTemplate.postForEntity(url, notificationDTO, String.class)).thenReturn(response);

        assertThrows(WebServiceException.class, () -> this.sendNotification.send(payer,notificationDTO.message()),
                "Email service is unavailable");

        Mockito.verify(this.restTemplate).postForEntity(url, notificationDTO, String.class);
        Mockito.verifyNoMoreInteractions(this.restTemplate);

    }
}