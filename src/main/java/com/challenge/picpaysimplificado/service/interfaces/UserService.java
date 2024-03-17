package com.challenge.picpaysimplificado.service.interfaces;

import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;

public interface UserService {

    void createUser(CreateUserDTO createUserDTO);

    User getUser(Long id);

    void saveUser(User user);
}
