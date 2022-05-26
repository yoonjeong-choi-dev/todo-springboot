package com.yj.common.exceptions;

import com.yj.dto.exception.ResponseErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.Timestamp;

@Slf4j
@ControllerAdvice
public class CommonExceptionAdvice {
    @ExceptionHandler({ContentNotFoundException.class, UserAuthenticationException.class, HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> notFoundError(Exception e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ResponseErrorDTO response = errorFromException(e, status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {NotAuthorizedException.class})
    public ResponseEntity<?> notAuthorizedException(Exception e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        ResponseErrorDTO response = errorFromException(e, status);

        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(value = {UserAlreadyExistException.class, InvalidRequestException.class})
    public ResponseEntity<?> badRequest(Exception e) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ResponseErrorDTO response = errorFromException(e, status);

        return new ResponseEntity<>(response, status);
    }

//    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
//    public ResponseEntity<?> unsupportedMethod(Exception e) {
//
//    }

    private ResponseErrorDTO errorFromException(Exception e, HttpStatus status) {
        return ResponseErrorDTO.builder()
                .message(e.getMessage())
                .status(status)
                .type(e.getClass().getSimpleName())
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    public ResponseEntity<?> generalException(Exception e) {
        Timestamp curTime = new Timestamp(System.currentTimeMillis());

        log.error("============ Internal Server Error ============");
        log.error(curTime.toString());
        log.error(e.getClass().getName());
        log.error(e.getMessage());
        e.printStackTrace();

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseErrorDTO response = ResponseErrorDTO.builder()
                .message("Server Error... Please contact us with yjchoi7166@gmail.com")
                .status(status)
                .timestamp(curTime)
                .build();

        return new ResponseEntity<>(response, status);
    }
}
