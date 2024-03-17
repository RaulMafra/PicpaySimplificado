package com.challenge.picpaysimplificado.service.impl;

import com.challenge.picpaysimplificado.domain.entity.Transaction;
import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.domain.entity.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.TransactionDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.repository.TransactionRepository;
import com.challenge.picpaysimplificado.service.interfaces.TransactionService;
import com.challenge.picpaysimplificado.webservices.AuthorizationService;
import com.challenge.picpaysimplificado.webservices.SendNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if(Stream.of(transactionDTO.value(), transactionDTO.receiver(), transactionDTO.payer()).anyMatch(Objects::isNull)){
            throw new TransactionException("There is some field empty");
        }
        User payer = this.userService.getUser(transactionDTO.payer());
        User receiver = this.userService.getUser(transactionDTO.receiver());

        this.verifyUserType(payer);
        this.checkBalance(payer, transactionDTO.value());

        this.verifyAuthorization();

        receiver.setBalance(receiver.getBalance().add(transactionDTO.value()));
        payer.setBalance(payer.getBalance().subtract(transactionDTO.value()));

        Transaction transaction = new Transaction(transactionDTO.value(), transactionDTO.payer(), transactionDTO.receiver(),
                payer.getUserType());

        this.saveTransaction(transaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(receiver);

        this.sendNotification.send();

    }

    private void verifyUserType(User payer){
        if(!(payer.getUserType().equals(UserType.COMMON))){
            throw new TransactionException("Company does not can make transfer");
        }
    }

    private void checkBalance(User payer, BigDecimal value){
        if(payer.getBalance().compareTo(value) < 0){
            throw new TransactionException("Balance of the payer insufficient");
        }
    }

    private void verifyAuthorization(){
        if(this.authorizationService.verifyAuthorization() != HttpStatus.OK){
            throw new TransactionException("Transaction denied");
        }
    }

    private void saveTransaction(Transaction transaction){
        this.transactionRepository.save(transaction);
    }
}
