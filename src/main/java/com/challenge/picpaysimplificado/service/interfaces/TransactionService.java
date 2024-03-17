package com.challenge.picpaysimplificado.service.interfaces;

import com.challenge.picpaysimplificado.dto.request.TransactionDTO;

public interface TransactionService {

    void makePayment(TransactionDTO transactionDTO);
}
