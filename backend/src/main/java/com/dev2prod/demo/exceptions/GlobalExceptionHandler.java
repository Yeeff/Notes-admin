package com.dev2prod.demo.exceptions;

import com.dev2prod.demo.domain.dtos.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleGenericExceptions(RuntimeException ex){
        return new ResponseEntity<ErrorResponse>(
                ErrorResponse.builder()
                        .errorCode("INTERNAL SERVER ERROR")
                        .errorMessage(ex.getMessage())
                        .build()
                ,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<Map<String,String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    ResponseEntity<ErrorResponse> handlerEntityAlreadyExistException(EntityAlreadyExistException ex){

        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.builder()
                .errorCode("ENTITY EXISTS")
                .errorMessage(ex.getMessage())
                .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ErrorResponse> handlerResourceNotFoundException(ResourceNotFoundException ex){

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.builder()
                .errorCode("ENTITY EXISTS")
                .errorMessage(ex.getMessage())
                .build());
    }
}
