package com.petrotal.ahcbackend.controller;

import com.petrotal.ahcbackend.dto.ErrorResponse;
import com.petrotal.ahcbackend.dto.ResponseDto;
import com.petrotal.ahcbackend.exception.*;
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
        ErrorResponse errorResponse = new ErrorResponse("Datos no válidos", errors);
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

    @ExceptionHandler(ConvertPdfException.class)
    public ResponseEntity<ResponseDto> convertPdfException(ConvertPdfException e) {
        String errorMessage = "ConvertPdfException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error al Convertir Archivo", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ResponseDto> fileStorageException(FileStorageException e) {
        String errorMessage = "FileStorageException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error al Manejar Archivo", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ModifiedDataException.class)
    public ResponseEntity<ResponseDto> modifiedDataException(ModifiedDataException e) {
        String errorMessage = "ModifiedDataException: " + e.getMessage();
        log.error(errorMessage);
        ErrorResponse errorResponse = new ErrorResponse("Error al Actualizar", e.getMessage());
        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ResponseDto> userAuthenticationException(UserAuthenticationException e) {
        String errorMessage = "UserAuthenticationException: " + e.getMessage();

        log.error(errorMessage);

        ErrorResponse errorResponse = new ErrorResponse("Error en el Proceso de Autenticación", e.getMessage());

        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ReportGeneratorException.class)
    public ResponseEntity<ResponseDto> reportGeneratorException(ReportGeneratorException e) {
        String errorMessage = "ReportGeneratorException: " + e.getMessage();

        log.error(errorMessage);

        ErrorResponse errorResponse = new ErrorResponse("Error de Acceso", e.getMessage());

        return new ResponseEntity<>(
                new ResponseDto(
                        errorResponse,
                        false)
                , HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
