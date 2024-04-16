package com.challenge.picpaysimplificado.webservices;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.dto.request.NotificationDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    public void send(User user, String message) {
        NotificationDTO notificationDTO = new NotificationDTO(user.getEmail(), message);
        ResponseEntity<String> notificationResponse;
        try {
            notificationResponse = this.restTemplate.postForEntity(url, notificationDTO, String.class);
        } catch (RestClientException e) {
            throw new WebServiceException("Email service is unavailable");
        }
        if (notificationResponse.getStatusCode() != HttpStatus.OK) {
            throw new WebServiceException("Doesn't was possible to send the notification");
        }
    }

}
