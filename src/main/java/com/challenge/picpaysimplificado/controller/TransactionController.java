package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.dto.response.ResponsePaymentDTO;
import com.challenge.picpaysimplificado.dto.response.ResponseErrorDTO;
import com.challenge.picpaysimplificado.service.impl.TransactionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/picpay-simplificado/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Operation(operationId = "payment", summary = "Make a payment", description = "Make a payment of common user for merchant or of common user for common user", tags = {"Transaction"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Payment done", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Value incorrect or empty, payer is merchant or payer doesn't has sufficient balance", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "default", description = "Unexpected error", content = @Content(mediaType = "application/json"))})
    @PostMapping("/payment")
    public ResponseEntity<ResponsePaymentDTO> makePayment(@RequestBody TransactionDTO transactionDTO) {
        this.transactionServiceImpl.makePayment(transactionDTO);
        return new ResponseEntity<>(new ResponsePaymentDTO("Payment done"), HttpStatus.OK);
    }

}
