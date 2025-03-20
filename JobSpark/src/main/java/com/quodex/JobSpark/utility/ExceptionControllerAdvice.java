package com.quodex.JobSpark.utility;

import com.quodex.JobSpark.exception.JobSparkException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler for handling application-wide exceptions.
 * Uses @RestControllerAdvice to provide centralized exception handling.
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Handles all general exceptions in the application.
     *
     * @param exception - The exception that occurred
     * @return ResponseEntity containing an ErrorInfo object with details about the error
     */

    @Autowired
    private Environment environment;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> generalException(Exception exception) {
        // Creating an ErrorInfo object with the exception message, HTTP status code, and timestamp
        ErrorInfo error = new ErrorInfo(
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JobSparkException.class)
    public ResponseEntity<ErrorInfo> generalException(JobSparkException exception) {
        // Retrieve a user-friendly error message from the environment properties
        String message = environment.getProperty(exception.getMessage());

        // Creating an ErrorInfo object with the error message, HTTP status code, and current timestamp
        ErrorInfo error = new ErrorInfo(
                message,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        // Returning a ResponseEntity with the error details and HTTP status code
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Handles validation-related exceptions, such as:
     * - MethodArgumentNotValidException (for invalid method arguments in request bodies)
     * - ConstraintViolationException (for validation errors on request parameters
     */

    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorInfo> validatorExceptionHandler(Exception exception) {
        String message = ""; // Variable to store the error message

        // Check if the exception is an instance of MethodArgumentNotValidException
        if (exception instanceof MethodArgumentNotValidException manvException) {
            // Extracts validation error messages from all errors and joins them into a single string
            message = manvException.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining(", "));
        } else {
            // Handle ConstraintViolationException separately
            ConstraintViolationException cvException = (ConstraintViolationException) exception;
            // Extracts validation error messages from constraint violations and joins them into a single string
            message = cvException.getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(", "));
        }

        // Create an ErrorInfo object with the error details
        ErrorInfo error = new ErrorInfo(
                message,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);  //Return a ResponseEntity with a BAD_REQUEST (400) status and the error details
    }

}
