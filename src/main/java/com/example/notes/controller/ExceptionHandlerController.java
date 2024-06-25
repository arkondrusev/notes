package com.example.notes.controller;

import com.example.notes.dto.OperationResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(RuntimeException.class)
    public OperationResponse handleRuntimeException(Exception e) {
        return OperationResponse.error(e.getMessage());
    }

}
