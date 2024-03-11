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

    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_transaction")
    private UUID id;
    @ManyToOne(cascade = CascadeType.ALL)
    private BigDecimal value;
    private Long id_payer;
    private Long id_payee;
    private UserType userType;
    @JoinColumns(value = @JoinColumn(name = "id_user"), foreignKey = @ForeignKey(name = "id_user"))
    private User user;
    private LocalDateTime timestamp;

}
