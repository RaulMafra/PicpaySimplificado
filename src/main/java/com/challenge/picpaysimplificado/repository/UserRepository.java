package com.challenge.picpaysimplificado.repository;

import com.challenge.picpaysimplificado.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
