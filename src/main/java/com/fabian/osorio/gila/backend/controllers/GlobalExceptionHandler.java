package com.fabian.osorio.gila.backend.controllers;

import com.fabian.osorio.gila.backend.model.CategoryEnum;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        String invalidValue = ex.getValue().toString();
        String enumValues = String.join(", ", CategoryEnum.validValues());

        String errorMessage = "Invalid value for 'category'. Allowed values are: " + enumValues;

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }
}
