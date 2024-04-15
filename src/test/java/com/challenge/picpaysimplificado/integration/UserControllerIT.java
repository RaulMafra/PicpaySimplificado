package com.challenge.picpaysimplificado.integration;

import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.dto.response.ResponseDTO;
import com.challenge.picpaysimplificado.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerIT {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    void mustCreateCommonUserAndReturnStatusCode201() {
        CreateUserDTO payer = new CreateUserDTO("payer", "any", "12345678900", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        HttpEntity<CreateUserDTO> body = new HttpEntity<>(payer);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/createUser",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertTrue(this.userRepository.findByDocument("12345678900").isPresent()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("User created with successfully"), response.getBody())
        );

    }

    @Test
    @Order(2)
    void mustCreateMerchantUserAndReturnStatusCode201() {
        CreateUserDTO receiver = new CreateUserDTO("receiver", "any", "12345678909630", "receiver2@email.com", "123",
                new BigDecimal(50), UserType.MERCHANT);

        HttpEntity<CreateUserDTO> body = new HttpEntity<>(receiver);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/createUser",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertTrue(this.userRepository.findByDocument("12345678909630").isPresent()),
                () -> Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("User created with successfully"), response.getBody())
        );

    }

    @Test
    void mustGetAUserAndReturnStatus200(){
        ResponseEntity<GetUserDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/1",
                         HttpMethod.GET, null, GetUserDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void mustFailedToTheGetAUserIfNotFound(){
        ResponseEntity<GetUserDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/5",
                        HttpMethod.GET, null, GetUserDTO.class);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertEquals(Optional.empty(), this.userRepository.findById(5L));
    }

    @Test
    void mustFailedIfTryCreateUserWithAEmailExisting(){
        CreateUserDTO payer = new CreateUserDTO("payer", "any", "12345678910", "payer@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        HttpEntity<CreateUserDTO> body = new HttpEntity<>(payer);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/createUser",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertEquals(new ResponseDTO("Conflict when persisting"), response.getBody());
    }

    @Test
    void mustFailedIfTryCreateUserWithADocumentExisting(){
        CreateUserDTO payer = new CreateUserDTO("payer", "any", "12345678909", "payer2@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        HttpEntity<CreateUserDTO> body = new HttpEntity<>(payer);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/createUser",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        Assertions.assertEquals(new ResponseDTO("Conflict when persisting"), response.getBody());

    }

    @Test
    void mustFailedIfInTheRequestExistSomeEmptyField(){
        CreateUserDTO payer = new CreateUserDTO(null, "any", "12345678901", null, "123",
                new BigDecimal(50), UserType.COMMON);

        HttpEntity<CreateUserDTO> body = new HttpEntity<>(payer);

        ResponseEntity<ResponseDTO> response =
                this.testRestTemplate.exchange("/picpay-simplificado/v1/users/createUser",
                        HttpMethod.POST, body, ResponseDTO.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals(Optional.empty(), this.userRepository.findByDocument("12345678901")),
                () -> Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> Assertions.assertEquals(new ResponseDTO("There is some property incorrect or empty"), response.getBody())
        );
    }

}
