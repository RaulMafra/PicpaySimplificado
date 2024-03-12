package com.challenge.picpaysimplificado.domain.entity;

import com.challenge.picpaysimplificado.domain.entity.enumerator.UserType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_transaction")
    private UUID id;
    @Column(name = "trn_value")
    private BigDecimal value;
    @JoinColumns(value = @JoinColumn(name = "id_payer"), foreignKey = @ForeignKey(name = "id_user"))
    private Long id_payer;
    @JoinColumns(value = @JoinColumn(name = "id_receiver"), foreignKey = @ForeignKey(name = "id_user"))
    private Long id_receiver;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    private LocalDateTime timestamp;

    public Transaction(BigDecimal value, Long id_payer, Long id_receiver, UserType userType){
        this.value = value;
        this.id_payer = id_payer;
        this.id_receiver = id_receiver;
        this.userType = userType;
        this.timestamp = LocalDateTime.now();
    }

}
