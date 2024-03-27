package com.challenge.picpaysimplificado.service.impl;

import com.challenge.picpaysimplificado.domain.Transaction;
import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.repository.TransactionRepository;
import com.challenge.picpaysimplificado.service.interfaces.TransactionService;
import com.challenge.picpaysimplificado.webservices.AuthorizationService;
import com.challenge.picpaysimplificado.webservices.SendNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SendNotification sendNotification;


    @Override
    public void makePayment(TransactionDTO transactionDTO) {
        if(Stream.of(transactionDTO.amount(), transactionDTO.receiver(), transactionDTO.payer()).anyMatch(Objects::isNull)){
            throw new TransactionException("There is some property incorrect or empty");
        }
        User payer = this.userService.getUser(transactionDTO.payer());
        User receiver = this.userService.getUser(transactionDTO.receiver());

        this.verifyUserType(payer);
        this.checkBalance(payer, transactionDTO.amount());

        this.authorizationService.verifyAuthorization(payer);

        receiver.setBalance(receiver.getBalance().add(transactionDTO.amount()));
        payer.setBalance(payer.getBalance().subtract(transactionDTO.amount()));

        Transaction transaction = new Transaction(transactionDTO.amount(), payer, receiver);

        this.saveTransaction(transaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(receiver);

        this.sendNotification.send(payer, "Pagamento realizado com sucesso");

    }

    private void verifyUserType(User payer){
        if(!(payer.getUserType().equals(UserType.COMMON))){
            throw new TransactionException("Merchant does not can make payment");
        }
    }

    private void checkBalance(User payer, BigDecimal value){
        if(payer.getBalance().compareTo(value) < 0){
            throw new TransactionException("Balance of the payer insufficient");
        }
    }


    private void saveTransaction(Transaction transaction){
        this.transactionRepository.save(transaction);
    }
}
