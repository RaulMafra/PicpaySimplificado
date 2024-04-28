package com.challenge.picpaysimplificado.service.impl;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.repository.TransactionRepository;
import com.challenge.picpaysimplificado.webservices.authorizationservice.AuthorizationService;
import com.challenge.picpaysimplificado.webservices.notificationservice.SendNotification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {


    @Mock
    TransactionRepository transactionRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    AuthorizationService authorizationService;

    @Mock
    SendNotification sendNotification;

    @InjectMocks
    TransactionServiceImpl transactionService;

    User payer;

    User receiver;

    @BeforeEach
    void init() {
        this.payer = new User(1L, "payer", "any", "12345678909", "payer@email.com", "123", new BigDecimal(0), UserType.COMMON);
        this.receiver = new User(2L, "receiver", "any", "12345678909632", "receiver@email.com", "123", new BigDecimal(0), UserType.MERCHANT);

    }

    @Test
    void mustMakeTransferWithSuccessfullyOfUserCommonForUserMerchant() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        payer.setBalance(new BigDecimal(50));

        Mockito.when(this.userService.getUser(1L)).thenReturn(payer);
        Mockito.when(this.userService.getUser(2L)).thenReturn(receiver);

        assertAll(
                () -> assertDoesNotThrow(() -> this.transactionService.makePayment(transactionDTO)),
                () -> assertDoesNotThrow(() -> this.authorizationService.verifyAuthorization(payer)),
                () -> assertEquals(new BigDecimal(40), payer.getBalance()),
                () -> assertEquals(new BigDecimal(10), receiver.getBalance())
        );

        Mockito.verify(this.userService).getUser(1L);
        Mockito.verify(this.userService).getUser(2L);
        Mockito.verify(this.authorizationService, Mockito.times(2)).verifyAuthorization(payer);
        Mockito.verify(this.transactionRepository).save(Mockito.any());
        Mockito.verify(this.userService).saveUser(payer);
        Mockito.verify(this.userService).saveUser(receiver);
        Mockito.verify(this.sendNotification).send(payer, "Pagamento realizado com sucesso");
        Mockito.verifyNoMoreInteractions(authorizationService, transactionRepository, userService, sendNotification);
    }

    @Test
    void mustMakeTransferWithSuccessfullyOfUserCommonForUserCommon() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        payer.setBalance(new BigDecimal(50));
        receiver.setUserType(UserType.COMMON);

        Mockito.when(this.userService.getUser(1L)).thenReturn(payer);
        Mockito.when(this.userService.getUser(2L)).thenReturn(receiver);

        assertAll(
                () -> assertDoesNotThrow(() -> this.transactionService.makePayment(transactionDTO)),
                () -> assertDoesNotThrow(() -> this.authorizationService.verifyAuthorization(payer)),
                () -> assertEquals(new BigDecimal(40), payer.getBalance()),
                () -> assertEquals(new BigDecimal(10), receiver.getBalance())
        );

        Mockito.verify(this.userService).getUser(1L);
        Mockito.verify(this.userService).getUser(2L);
        Mockito.verify(this.authorizationService, Mockito.times(2)).verifyAuthorization(payer);
        Mockito.verify(this.transactionRepository).save(Mockito.any());
        Mockito.verify(this.userService).saveUser(payer);
        Mockito.verify(this.userService).saveUser(receiver);
        Mockito.verify(this.sendNotification).send(payer, "Pagamento realizado com sucesso");
        Mockito.verifyNoMoreInteractions(authorizationService, transactionRepository, userService, sendNotification);
    }

    @Test
    void mustFailedIfPayerIsMerchant() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        Mockito.when(this.userService.getUser(1L)).thenReturn(payer);
        Mockito.when(this.userService.getUser(2L)).thenReturn(receiver);

        assertAll(
                () -> assertEquals(new BigDecimal(0), this.payer.getBalance()),
                () -> assertEquals(new BigDecimal(0), this.receiver.getBalance()),
                () -> assertThrows(TransactionException.class, () -> this.transactionService.makePayment(transactionDTO),
                        "Merchant does not can make transfer")
        );

        Mockito.verify(this.userService).getUser(1L);
        Mockito.verify(this.userService).getUser(2L);
        Mockito.verifyNoInteractions(this.authorizationService, this.transactionRepository, this.sendNotification);
        Mockito.verifyNoMoreInteractions(this.userService);

    }

    @Test
    void mustFailedIfSomeFieldBeNullOfTheTransactionDTO() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(10), null, 2L);

        assertAll(
                () -> assertEquals(new BigDecimal(0), this.payer.getBalance()),
                () -> assertEquals(new BigDecimal(0), this.receiver.getBalance()),
                () -> assertThrows(TransactionException.class, () -> this.transactionService.makePayment(transactionDTO),
                        "There is some property incorrect or empty")
        );

        Mockito.verifyNoInteractions(this.authorizationService, this.transactionRepository, this.userService, this.sendNotification);
    }



    @Test
    void mustFailedIfBalancePayerIsInsufficient() {
        TransactionDTO transactionDTO = new TransactionDTO(new BigDecimal(10), 1L, 2L);

        Mockito.when(this.userService.getUser(1L)).thenReturn(payer);
        Mockito.when(this.userService.getUser(2L)).thenReturn(receiver);

        assertAll(
                () -> assertEquals(new BigDecimal(0), this.payer.getBalance()),
                () -> assertEquals(new BigDecimal(0), this.receiver.getBalance()),
                () -> assertThrows(TransactionException.class, () -> this.transactionService.makePayment(transactionDTO),
                        "Balance of the payer insufficient")
        );

        Mockito.verify(this.userService).getUser(1L);
        Mockito.verify(this.userService).getUser(2L);
        Mockito.verifyNoInteractions(this.authorizationService, this.transactionRepository, this.sendNotification);
        Mockito.verifyNoMoreInteractions(this.userService);
    }

}