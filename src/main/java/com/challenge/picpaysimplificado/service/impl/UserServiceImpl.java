package com.challenge.picpaysimplificado.service.impl;

import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserNotFound;
import com.challenge.picpaysimplificado.repository.UserRepository;
import com.challenge.picpaysimplificado.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        if(Stream.of(createUserDTO.name(), createUserDTO.document(), createUserDTO.email(), createUserDTO.password(), createUserDTO.balance(),
                createUserDTO.userType()).anyMatch(Objects::isNull)){
            throw new UserException("There is some field empty");
        }
        User newUser = new User(createUserDTO);
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        this.userRepository.save(newUser);
    }

    @Override
    public User getUser(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));

    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

}
