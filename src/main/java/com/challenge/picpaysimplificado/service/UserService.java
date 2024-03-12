package com.challenge.picpaysimplificado.service;

import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;

public interface UserService {

    void createUser(CreateUserDTO createUserDTO);
    GetUserDTO getUser(Long id);
}
