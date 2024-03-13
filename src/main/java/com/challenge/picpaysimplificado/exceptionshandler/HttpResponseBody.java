package com.challenge.picpaysimplificado.exceptionshandler;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class HttpResponseBody {

    private LocalDateTime timestamp;
    private int statusCode;
    private HttpStatus status;
    private String message;
    private String path;

    public HttpResponseBody(int statusCode, HttpStatus status, String message, String path){
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
