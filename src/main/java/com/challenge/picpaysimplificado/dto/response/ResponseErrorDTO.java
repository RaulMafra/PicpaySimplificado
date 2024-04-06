package com.challenge.picpaysimplificado.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ResponseErrorDTO(LocalDateTime timestamp, int statusCode, HttpStatus status, String message, String path) { }
