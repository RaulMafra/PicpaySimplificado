package com.challenge.picpaysimplificado.controller;

import com.challenge.picpaysimplificado.domain.User;
import com.challenge.picpaysimplificado.dto.request.CreateUserDTO;
import com.challenge.picpaysimplificado.dto.response.GetUserDTO;
import com.challenge.picpaysimplificado.dto.response.ResponsePaymentDTO;
import com.challenge.picpaysimplificado.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.StringToClassMapItem;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/picpay-simplificado/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Operation(operationId = "createUser", summary = "Add a new common user or merchant user", description = "Add a new common user or merchant user for make bank transfer", tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "User created with successfully", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "400", description = "Value incorrect or empty", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "409", description = "Already exists a user with same document or email", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "default", description = "Unexpected error", content = @Content(mediaType = "application/json")),})
    @PostMapping("/createUser")
    public ResponseEntity<ResponsePaymentDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        this.userServiceImpl.createUser(createUserDTO);
        return new ResponseEntity<>(new ResponsePaymentDTO("User created with successfully"), HttpStatus.CREATED);
    }

    @Operation(operationId = "getUser", summary = "Get a user through of the your id", description = "Get a user through of the your id", tags = {"User"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "A User object", content = {@Content(mediaType = "application/json",
                            schema = @Schema(type = "object", name = "CreateUserDTO", properties = @StringToClassMapItem(key = "GetUserDTO", value = GetUserDTO.class)))}),
                    @ApiResponse(responseCode = "400", description = "User not found", content = {@Content(mediaType = "application/json")}),
                    @ApiResponse(responseCode = "default", description = "Unexpected error", content = @Content(mediaType = "application/json"))})
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDTO> getUser(@PathVariable Long id) {
        User user = this.userServiceImpl.getUser(id);
        GetUserDTO getUserDTO = new GetUserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getDocument(),
                user.getEmail(), user.getPassword(), user.getBalance(), user.getUserType());
        return new ResponseEntity<>(getUserDTO, HttpStatus.OK);
    }
}
