package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.service.impl.TransactionServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    MockMvc mockMvc;

    @Mock
    TransactionServiceImpl transactionService;

    @InjectMocks
    TransactionController transactionController;

    URI uri;

    @SneakyThrows
    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .alwaysDo(MockMvcResultHandlers.print()).build();
        this.uri = new URI("/restful/v1/transactions/payment");
    }

    @Test
    void shouldMakePaymentAndReturnStatusCode200() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(50), 1L, 2L);

        Mockito.doNothing().when(this.transactionService).makePayment(transactionDTO);

        this.mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(new ObjectMapper().writeValueAsString(transactionDTO)))
                .andExpectAll(status().isOk(),
                        content().string("{\"message\":\"Payment done\"}"))
                .andReturn();

        Mockito.verify(this.transactionService).makePayment(transactionDTO);
        Mockito.verifyNoMoreInteractions(this.transactionService);
    }
}