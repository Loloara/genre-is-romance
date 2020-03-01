package com.loloara.genreisromance.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter @Getter
public class ApiException extends RuntimeException {

    private String exceptionCode;
    private HttpStatus httpStatus;

    public ApiException(Throwable cause, HttpStatus httpStatus) {
        super(cause);
        setHttpStatus(httpStatus);
    }

    public ApiException(String message,HttpStatus httpStatus) {
        super(message);
        setHttpStatus(httpStatus);
    }

    public ApiException(String message) {
        super(message);
        setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
