package com.challenge.picpaysimplificado.dto.request;

import com.challenge.picpaysimplificado.domain.entity.enumerator.UserType;

import java.math.BigDecimal;

public record CreateUserDTO(String name, String document, String email, String password, BigDecimal balance, UserType userType) {
}
