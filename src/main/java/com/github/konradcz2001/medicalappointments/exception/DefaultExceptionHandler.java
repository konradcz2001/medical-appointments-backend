package com.github.konradcz2001.medicalappointments.exception;

import com.github.konradcz2001.medicalappointments.exception.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * This code snippet represents a class named DefaultExceptionHandler.
 * It is a controller advice class that handles various exceptions and generates appropriate API error responses.
 * The class has several exception handler methods annotated with @ExceptionHandler.
 * - handleNotFoundException method handles ResourceNotFoundException and EmptyPageException, and returns a ResponseEntity with an ApiError object containing the error details.
 * - handleWrongDataException method handles WrongLeaveException, WrongSpecializationException, WrongReviewException, and ConstraintViolationException, and returns a ResponseEntity with an ApiError object containing the error details.
 * - handleAuthenticationException method handles InsufficientAuthenticationException, and returns a ResponseEntity with an ApiError object containing the error details.
 * - handleAuthorizationException method handles BadCredentialsException, and returns a ResponseEntity with an ApiError object containing the error details.
 * - handleException method handles any other exception, and returns a ResponseEntity with an ApiError object containing the error details.
 * The ApiError class is a record class that represents an API error and provides a convenient way to encapsulate the error details.
 * The class has fields for path, message, status, and localDateTime, and is designed to be used as an immutable data holder.
 * The class also has a constructor that sets the field values, and the fields are accessed using getter methods.
 * The HttpStatus class from the org.springframework.http package is used to define the HTTP status codes for the error responses.
 * The LocalDateTime class from the java.time package is used to get the current local date and time when an error occurs.
 */
@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, EmptyPageException.class})
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                NOT_FOUND.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler({WrongLeaveException.class, WrongSpecializationException.class, WrongScheduleException.class,
            WrongReviewException.class, ConstraintViolationException.class, WrongTypeOfVisitException.class})
    public ResponseEntity<ApiError> handleWrongDataException(RuntimeException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(InsufficientAuthenticationException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                FORBIDDEN.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, FORBIDDEN);
    }

    @ExceptionHandler({BadCredentialsException.class, WrongRoleException.class, DuplicateEmailException.class})
    public ResponseEntity<ApiError> handleAuthorizationException(BadCredentialsException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                UNAUTHORIZED.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                errorMessage,
                BAD_REQUEST.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }


}
