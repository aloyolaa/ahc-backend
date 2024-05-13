package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ErrorResponse;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.exception.DataAccessExceptionImpl;
import com.petrotal.ahcbackend.exception.InvalidVariableException;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String errorMessage = "MethodArgumentNotValidException: Datos no válidos.";
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error al Guardar Datos", errors);
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidVariableException.class)
    public ResponseEntity<ResponseDto> invalidVariableException(InvalidVariableException e) {
        String errorMessage = "InvalidVariableException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error de Acceso", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataAccessExceptionImpl.class)
    public ResponseEntity<ResponseDto> dataAccessExceptionImpl(DataAccessExceptionImpl e) {
        String errorMessage = "DataAccessException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error de Acceso", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseDto> entityNotFoundException(EntityNotFoundException e) {
        String errorMessage = "EntityNotFoundException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error en Búsqueda de Datos", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
