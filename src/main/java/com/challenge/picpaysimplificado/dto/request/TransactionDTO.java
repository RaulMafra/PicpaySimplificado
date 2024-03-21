package com.challenge.picpaysimplificado.dto.request;

import java.math.BigDecimal;

public record TransactionDTO(BigDecimal amount, Long payer, Long receiver) {
}
