package io.github.onlineshop.web;

import io.github.onlineshop.orders.domain.exception.InsufficientStockException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log =
        LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception e) {
        log.error("Handle exception: ", e);

        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Internal server error",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(errorDto);
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponseDto> handleInsufficientStock(
        InsufficientStockException e
    ) {
        log.error("Handle Insufficient Stock exception: ", e);

        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Insufficient stock for product",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(errorDto);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleEntityNotFoundException(
        EntityNotFoundException e
    ) {
        log.error("Handle entity not found: ", e);

        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Entity not found",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(errorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e
    ) {
        log.error("Handle MethodArgumentNotValidException: ", e);

        List<String> errorMessages = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();
        String message = String.join("; ", errorMessages);
        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Validation failed",
            message,
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorDto);
    }

    @ExceptionHandler(exception = {
        IllegalArgumentException.class,
        IllegalStateException.class,
    })
    public ResponseEntity<ErrorResponseDto> handleBadRequest(Exception e) {
        log.error("Handle bad request: ", e);

        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Bad request",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(errorDto);
    }

    @ExceptionHandler(exception =  {
        BadCredentialsException.class
    })
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(
        BadCredentialsException e
    ) {
        log.error("Handle bad credentials: ", e);

        ErrorResponseDto errorDto = new ErrorResponseDto(
            "Bad credentials",
            e.getMessage(),
            LocalDateTime.now()
        );

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(errorDto);
    }
}
