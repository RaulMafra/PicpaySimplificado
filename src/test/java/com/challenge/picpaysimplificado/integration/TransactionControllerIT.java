package com.challenge.picpaysimplificado.integration;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.dto.response.ResponseDTO;
import com.challenge.picpaysimplificado.repository.TransactionRepository;
import com.challenge.picpaysimplificado.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionControllerIT {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void mustMakePaymentAndReturnStatusCode200AndSaveTransaction() {
        User payer = new User(1L,"payer", "any", "12345678909", "payer@email.com", "123",
                new BigDecimal(50), UserType.COMMON);
        User receiver = new User(2L,"receiver", "any", "12345678909632", "receiver@email.com", "123",
                new BigDecimal(50), UserType.MERCHANT);
        this.userRepository.save(payer);
        this.userRepository.save(receiver);

        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(20), 1L, 2L);

        HttpEntity<TransactionDTO> body = new HttpEntity<>(transactionDTO);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/transactions/payment",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("Payment done"), response.getBody()),
                () -> Assertions.assertEquals(1, this.transactionRepository.findAll().size()),
                () -> Assertions.assertEquals(new BigDecimal("30.00"), this.userRepository.findById(1L).get().getBalance()),
                () -> Assertions.assertEquals(new BigDecimal("70.00"), this.userRepository.findById(2L).get().getBalance())
        );

    }

    @Test
    void mustFailedIfPayerIsMerchant() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(50), 2L, 1L);

        HttpEntity<TransactionDTO> body = new HttpEntity<>(transactionDTO);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/transactions/payment",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("Merchant does not can make payment"), response.getBody()),
                () -> Assertions.assertEquals(new BigDecimal("30.00"), this.userRepository.findById(1L).get().getBalance()),
                () -> Assertions.assertEquals(new BigDecimal("70.00"), this.userRepository.findById(2L).get().getBalance())
        );

    }

    @Test
    void mustFailedIfThePayerBalanceBeInsufficient(){
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(50), 1L, 2L);

        HttpEntity<TransactionDTO> body = new HttpEntity<>(transactionDTO);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/transactions/payment",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("Balance of the payer insufficient"), response.getBody()),
                () -> Assertions.assertEquals(1, this.transactionRepository.findAll().size()),
                () -> Assertions.assertEquals(new BigDecimal("30.00"), this.userRepository.findById(1L).get().getBalance()),
                () -> Assertions.assertEquals(new BigDecimal("70.00"), this.userRepository.findById(2L).get().getBalance())
        );
    }

}
