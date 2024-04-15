package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.domain.enumerator.UserType;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    MockMvc mockMvc;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UserController userController;

    URI uri;

    @SneakyThrows
    @BeforeEach
    void init(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .alwaysDo(print()).build();
        this.uri = new URI("/picpay-simplificado/v1/users");
    }


    @Test
    void mustCreateCommonUserAndReturnStatusCode201() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("any", "any", "12345678909", "any@email.com", "123",
                new BigDecimal(50), UserType.COMMON);

        Mockito.doNothing().when(this.userService).createUser(createUserDTO);

        this.mockMvc.perform(post(uri + "/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .content(new ObjectMapper().writeValueAsString(createUserDTO)))
                .andExpectAll(status().isCreated(),
                        content().string("{\"message\":\"User created with successfully\"}"))
                .andReturn();

        Mockito.verify(this.userService).createUser(createUserDTO);
        Mockito.verifyNoMoreInteractions(this.userService);
    }

    @Test
    void mustCreateCompanyUserAndReturnStatusCode201() throws Exception {
        CreateUserDTO createUserDTO = new CreateUserDTO("any","any","12345678909234", "any@email.com", "123",
                new BigDecimal(50), UserType.MERCHANT);

        Mockito.doNothing().when(this.userService).createUser(createUserDTO);

        this.mockMvc.perform(post(uri + "/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .content(new ObjectMapper().writeValueAsString(createUserDTO)))
                .andExpectAll(status().isCreated(),
                        content().string("{\"message\":\"User created with successfully\"}"))
                .andReturn();

        Mockito.verify(this.userService).createUser(createUserDTO);
        Mockito.verifyNoMoreInteractions(this.userService);
    }

    @Test
    void mustGetAUserAndReturnStatus200() throws Exception {
        GetUserDTO getUserDTO = new GetUserDTO(1L, "any", "any", "12345678909", "any@email.com", "123",
                new BigDecimal(0), UserType.COMMON);
        User user = new User(getUserDTO.id(), getUserDTO.firstName(), getUserDTO.lastName(), getUserDTO.document(),
                getUserDTO.email(), getUserDTO.password(), getUserDTO.balance(), getUserDTO.userType());

        Mockito.when(this.userService.getUser(1L)).thenReturn(user);

        this.mockMvc.perform(get(uri + "/{id}", getUserDTO.id())
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8))
                .andExpectAll(status().isOk(),
                        content().string(new ObjectMapper().writeValueAsString(getUserDTO)))
                .andReturn();

        Mockito.verify(this.userService).getUser(1L);
        Mockito.verifyNoMoreInteractions(this.userService);

    }

}