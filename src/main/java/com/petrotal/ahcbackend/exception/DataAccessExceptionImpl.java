package com.petrotal.ahcbackend.exception;

import org.springframework.dao.DataAccessException;

public class DataAccessExceptionImpl extends DataAccessException {
    public DataAccessExceptionImpl(String message, Throwable throwable) {
        super(message, throwable);
    }
}
