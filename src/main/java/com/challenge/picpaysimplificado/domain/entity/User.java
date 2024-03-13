package com.challenge.picpaysimplificado.domain.entity;

import com.challenge.picpaysimplificado.domain.entity.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(unique = true, nullable = false)
    private String document;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private BigDecimal balance;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    public User(CreateUserDTO createUserDTO){
        this.name = createUserDTO.name();
        this.document = createUserDTO.document();
        this.email = createUserDTO.email();
        this.password = createUserDTO.password();
        this.balance = createUserDTO.balance();
        this.userType = createUserDTO.userType();
    }
}
