package com.loloara.genreisromance.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {
        Map<String, Object> message = new HashMap<>();
        message.put("errorMessage", e.getMessage());
        message.put("status", e.getHttpStatus());

        return new ResponseEntity<Object>(message, e.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e) {
        return new ResponseEntity<Object>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
