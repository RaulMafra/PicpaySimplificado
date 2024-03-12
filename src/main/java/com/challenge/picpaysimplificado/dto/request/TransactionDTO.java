package com.challenge.picpaysimplificado.dto.request;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal value, Long payer, Long receiver) {
}
