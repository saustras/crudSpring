package com.handle.handle.exception;

import com.handle.handle.utils.GetSoureceMessage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final GetSoureceMessage getSoureceMessage;


    public GlobalExceptionHandler(GetSoureceMessage getSoureceMessage) {
        this.getSoureceMessage = getSoureceMessage;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<Map<String, String>> handleArgumentExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            if (error instanceof FieldError fieldError) {
                String errorMessage = getMessage(fieldError);
                errors.put(fieldError.getField(), errorMessage);
            }
        });
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errors);
    }

    @ResponseBody
    @ExceptionHandler(value = {RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return this.buildErrorResponse(exception, exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(FieldError fieldError) {
        Object[] arguments = fieldError.getArguments();
        try {
            Object[] messageArguments = Optional.ofNullable(arguments)
                    .filter(args -> args.length > 2)
                    .map(args -> buildMessageArguments(fieldError, args))
                    .orElseGet(() -> new Object[]{fieldError.getField()});
            return getSoureceMessage.getMessageException(fieldError.getCode(), messageArguments);
        } catch (NoSuchMessageException e) {
            return fieldError.getDefaultMessage();
        }
    }

    private Object[] buildMessageArguments(FieldError fieldError, Object[] args) {
        return Stream.concat(
                Stream.of(fieldError.getField()),
                Arrays.stream(args)
        ).toArray(Object[]::new);
    }

    protected ResponseEntity<ErrorResponse> buildErrorResponse(Throwable e, String message, HttpStatus httpStatus) {
        log.error(e.getMessage());
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorResponse.builder()
                        .code(httpStatus.value())
                        .message(message)
                        .build());
    }
}
