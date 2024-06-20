package com.github.konradcz2001.medicalappointments.exception;

import com.github.konradcz2001.medicalappointments.exception.exceptions.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

/**
 * The DefaultExceptionHandler class is a controller advice class that handles various exceptions by providing custom responses.
 * It contains methods annotated with @ExceptionHandler to handle specific exceptions and return ResponseEntity<ApiError>.
 * The class provides exception handling for ResourceNotFoundException, EmptyPageException, WrongLeaveException, WrongSpecializationException,
 * WrongScheduleException, WrongReviewException, ConstraintViolationException, WrongTypeOfVisitException, WrongVisitException,
 * InsufficientAuthenticationException, AuthenticationException, DuplicateEmailException, WrongUserException, BadCredentialsException,
 * and MethodArgumentNotValidException. It also has a generic exception handler for any other type of Exception.
 * Each exception handler method constructs an ApiError object with details such as class name, request URI, error message, HTTP status code,
 * and local date and time, and returns a ResponseEntity with the ApiError object and corresponding HTTP status.
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
            WrongReviewException.class, ConstraintViolationException.class, WrongTypeOfVisitException.class,
            WrongVisitException.class})
    public ResponseEntity<ApiError> handleWrongDataException(RuntimeException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

    @ExceptionHandler({InsufficientAuthenticationException.class, AuthenticationException.class,
            DuplicateEmailException.class, WrongUserException.class})
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getClass().getSimpleName(),
                request.getRequestURI(),
                ex.getMessage(),
                FORBIDDEN.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, FORBIDDEN);
    }

    @ExceptionHandler({BadCredentialsException.class, WrongRoleException.class})
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
