package com.challenge.picpaysimplificado.dto.response;

import com.challenge.picpaysimplificado.domain.enumerator.UserType;

import java.math.BigDecimal;

public record GetUserDTO(Long id, String firstName, String lastName, String document, String email, String password, BigDecimal balance, UserType userType) {
}
