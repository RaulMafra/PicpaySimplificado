package com.challenge.picpaysimplificado.service.impl;

import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.domain.entity.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserException;
import com.challenge.picpaysimplificado.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@ActiveProfiles(value = "test")
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepository mockUserRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Autowired
    EntityManager entityManager;

    @Autowired
    UserRepository userRepository;

    @Test
    void mustCreateUserWithSuccessfully() {
        CreateUserDTO createUserDTO = new CreateUserDTO("any", "12345678909", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        assertDoesNotThrow(() -> this.userService.createUser(createUserDTO));

        Mockito.verify(this.mockUserRepository).save(Mockito.any());

    }

    @Test
    void mustFailedIfSomeFieldIsNull(){
        CreateUserDTO createUserDTO = new CreateUserDTO(null, "12345678909", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        assertThrows(UserException.class, () -> this.userService.createUser(createUserDTO),
                "There is some field empty");

        Mockito.verifyNoInteractions(this.mockUserRepository);
    }

    @Test
    void mustGetAUserWithSuccessfully() {
        User user = new User(1L, "any", "12345678909", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        this.saveUser(user);
        Assertions.assertThat(this.userRepository.findById(1L).isPresent()).isTrue();

    }

    void saveUser(User user) {
        Session session = this.entityManager.unwrap(Session.class);
        User attachedEntity = session.merge(user);
        this.entityManager.persist(attachedEntity);
    }
}