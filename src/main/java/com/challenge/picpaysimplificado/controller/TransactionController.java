package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.dto.response.HttpResponse;
import com.challenge.picpaysimplificado.service.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restful/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @PostMapping("/payment")
    public ResponseEntity<HttpResponse> makePayment(@RequestBody TransactionDTO transactionDTO){
        this.transactionServiceImpl.makePayment(transactionDTO);
        return new ResponseEntity<>(new HttpResponse("Payment done"), HttpStatus.OK);
    }

}
