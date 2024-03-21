package com.challenge.picpaysimplificado.repository;

import com.challenge.picpaysimplificado.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
