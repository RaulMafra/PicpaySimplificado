package com.challenge.picpaysimplificado.service;

import com.challenge.picpaysimplificado.dto.request.TransactionDTO;

public interface TransactionService {

    void makePayment(TransactionDTO transactionDTO);
}
