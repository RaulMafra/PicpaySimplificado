package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.dto.response.HttpResponse;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restful/v1/users")
public class UserController {

    @Autowired

    private UserServiceImpl userServiceImpl;


    @PostMapping("/createUser")
    public ResponseEntity<HttpResponse> createUser(@RequestBody CreateUserDTO createUserDTO){
        this.userServiceImpl.createUser(createUserDTO);
        return new ResponseEntity<>(new HttpResponse("User created with successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id){
        GetUserDTO getUserDTO = this.userServiceImpl.getUser(id);
        return new ResponseEntity<>(getUserDTO, HttpStatus.OK);
    }
}
