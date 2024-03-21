package com.challenge.picpaysimplificado.domain;

import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_transaction")
    private UUID id;
    @Column(name = "trn_amount")
    private BigDecimal amount;
    @JoinColumns(value = @JoinColumn(name = "payer"), foreignKey = @ForeignKey(name = "id_user"))
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User payer;
    @JoinColumns(value = @JoinColumn(name = "receiver"))
    @ManyToOne(cascade = CascadeType.PERSIST)
    private User receiver;
    private LocalDateTime timestamp;

    public Transaction(BigDecimal amount, User payer, User receiver){
        this.amount = amount;
        this.payer = payer;
        this.receiver = receiver;
        this.timestamp = LocalDateTime.now();
    }

}
