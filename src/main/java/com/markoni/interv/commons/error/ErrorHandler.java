package com.markoni.interv.commons.error;

import com.markoni.interv.commons.message.Message;
import com.markoni.interv.commons.message.MessageResponse;
import com.markoni.interv.commons.message.Severity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    /**
     * Handles NotFoundException, e.g when record was not found in DB by the given parameters
     */
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<?> handleValidationException(NotFoundException ex) {
        log.error(ex.getMessage(), ex);
        Message m = Message.builder().severity(Severity.ERROR).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MessageResponse.of(m));
    }

    /**
     * Handles ExceptionValidation, e.g business validation, constraint issues
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleValidationException(ValidationException ex) {
        log.error("Validation failed", ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessages());
    }


    /**
     * Handles bad formatted json request
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("HTTP message not readable", ex);
        Message m = Message.builder().severity(Severity.ERROR).message(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.of(m));
    }

    /**
     * Handles exceptions related to invalid request parameters (validation)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<?> handleMethodArgumentTypeNotValidException(MethodArgumentNotValidException ex) {
        log.error("Method argument not valid", ex);
        List<Message> messages = ex
            .getBindingResult()
            .getFieldErrors()
            .stream()
            .map(fe -> Message.builder().message(fe.getDefaultMessage()).severity(Severity.ERROR).build())
            .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse(messages));
    }

    /**
     * Handles HttpRequestMethodNotSupportedException exception e.g. when POST is sent, while supported is GET
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<?> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        log.error("HTTP request method not supported", ex);
        String supportedMethods = Objects
            .requireNonNull(ex.getSupportedHttpMethods())
            .stream()
            .map(HttpMethod::name)
            .collect(Collectors.joining(","));

        String message = ex.getMethod() + " method is not supported. Supported methods are " + supportedMethods;
        Message m = Message.builder().severity(Severity.ERROR).message(message).build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(MessageResponse.of(m));
    }

    /**
     * Default exception handler, any exception which occurs and covered by other handlers will be handled here
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<?> handleDefaultUncheckedException(Throwable th) {
        log.error("Unhandled error occurred", th);
        Message m = Message.builder().severity(Severity.ERROR).message("Unexpected error occurred").build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.of(m));
    }


}
