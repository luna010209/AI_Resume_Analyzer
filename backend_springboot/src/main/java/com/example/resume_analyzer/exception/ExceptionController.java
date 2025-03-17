package com.example.resume_analyzer.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleException(CustomException e){
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}
