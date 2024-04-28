package com.challenge.picpaysimplificado.webservices.authorizationservice.core;

import com.challenge.picpaysimplificado.domain.User;

public interface AuthorizationServiceUseCase {

    void verifyAuthorization(User payer);
}
