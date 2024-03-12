package com.challenge.picpaysimplificado.service;

import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.exceptions.TransactionException;
import com.challenge.picpaysimplificado.exceptions.UserException;
import com.challenge.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    private  UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public void createUser(CreateUserDTO createUserDTO) {
        if(Stream.of(createUserDTO.name(), createUserDTO.document(), createUserDTO.email()).anyMatch(Objects::isNull)){
            throw new TransactionException("There is some field empty");
        }
        User newUser = new User(createUserDTO);
        this.userRepository.save(newUser);
    }

    @Override
    public GetUserDTO getUser(Long id) {
        if(id == null) throw new UserException("Does not permitted document empty");
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserException("User not found"));
        return new GetUserDTO(user);

    }

}
