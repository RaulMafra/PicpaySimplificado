package com.challenge.picpaysimplificado.repository;

import com.challenge.picpaysimplificado.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findUserByDocument(String document);
}
