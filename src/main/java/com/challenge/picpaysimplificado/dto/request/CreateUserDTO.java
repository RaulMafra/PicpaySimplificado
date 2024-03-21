package com.challenge.picpaysimplificado.dto.request;

import com.challenge.picpaysimplificado.domain.enumerator.UserType;

import java.math.BigDecimal;

public record CreateUserDTO(String firstName, String lastName, String document, String email, String password, BigDecimal balance, UserType userType) {
}
