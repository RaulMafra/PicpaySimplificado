package com.challenge.picpaysimplificado.exceptionshandler;

import com.challenge.picpaysimplificado.dto.response.ErrorResponseDTO;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.TransactionException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserException;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.UserNotFound;
import com.challenge.picpaysimplificado.exceptionshandler.exceptions.WebServiceException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    private HttpHeaders headers(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    @ExceptionHandler(TransactionException.class)
    private ResponseEntity<ErrorResponseDTO> transactionException(TransactionException e, WebRequest request, HttpServletRequest httpServletRequest){
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

    @ExceptionHandler(UserNotFound.class)
    private ResponseEntity<ErrorResponseDTO> transactionException(UserNotFound e, WebRequest request, HttpServletRequest httpServletRequest){
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

    @ExceptionHandler(UserException.class)
    private ResponseEntity<ErrorResponseDTO> transactionException(UserException e, WebRequest request, HttpServletRequest httpServletRequest){
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

    @ExceptionHandler(WebServiceException.class)
    private ResponseEntity<ErrorResponseDTO> transactionException(WebServiceException e, WebRequest request, HttpServletRequest httpServletRequest){
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.SERVICE_UNAVAILABLE.value(), HttpStatus.SERVICE_UNAVAILABLE, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    private ResponseEntity<ErrorResponseDTO> handleDataPersistence(DataIntegrityViolationException e, WebRequest request, HttpServletRequest httpServletRequest){
        String response = "Conflict when persisting";
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT, response, httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

    @ExceptionHandler(RuntimeException.class)
    private ResponseEntity<ErrorResponseDTO> transactionException(RuntimeException e, WebRequest request, HttpServletRequest httpServletRequest){
        ErrorResponseDTO responseError = new ErrorResponseDTO(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), httpServletRequest.getRequestURI());
        return new ResponseEntity<>(responseError, headers(), responseError.status());
    }

}
