package com.challenge.picpaysimplificado.service;

import com.challenge.picpaysimplificado.domain.entity.User;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserNotFound;
import com.challenge.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.beans.Encoder;
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
        if(Stream.of(createUserDTO.name(), createUserDTO.document(), createUserDTO.email(), createUserDTO.password(), createUserDTO.balance(),
                createUserDTO.userType()).anyMatch(Objects::isNull)){
            throw new UserException("There is some field empty");
        }
        User newUser = new User(createUserDTO);
        newUser.setPassword(new BCryptPasswordEncoder().encode(newUser.getPassword()));
        this.userRepository.save(newUser);
    }

    @Override
    public GetUserDTO getUser(Long id) {
        if(id == null) throw new UserException("Does not permitted document empty");
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound("User not found"));
        return new GetUserDTO(user);

    }

}
