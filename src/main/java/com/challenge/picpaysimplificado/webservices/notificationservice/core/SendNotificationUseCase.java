package com.challenge.picpaysimplificado.webservices.notificationservice.core;

import com.challenge.picpaysimplificado.domain.User;

public interface SendNotificationUseCase {

    void send(User user, String email);
}
