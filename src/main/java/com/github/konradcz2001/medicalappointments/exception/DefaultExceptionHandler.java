package com.github.konradcz2001.medicalappointments.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;


@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class, EmptyPageException.class})
    public ResponseEntity<ApiError> handleNotFoundException(RuntimeException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                NOT_FOUND.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, NOT_FOUND);
    }

    @ExceptionHandler({WrongLeaveException.class, WrongSpecializationException.class, ConstraintViolationException.class})
    public ResponseEntity<ApiError> handleWrongDataException(RuntimeException ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                BAD_REQUEST.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, BAD_REQUEST);
    }

//    @ExceptionHandler(InsufficientAuthenticationException.class)
//    public ResponseEntity<ApiError> handleAuthenticationException(InsufficientAuthenticationException ex, HttpServletRequest request){
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                ex.getMessage(),
//                FORBIDDEN.value(),
//                LocalDateTime.now());
//
//        return new ResponseEntity<>(apiError, FORBIDDEN);
//    }
//
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<ApiError> handleAuthorizationException(BadCredentialsException ex, HttpServletRequest request){
//        ApiError apiError = new ApiError(
//                request.getRequestURI(),
//                ex.getMessage(),
//                UNAUTHORIZED.value(),
//                LocalDateTime.now());
//
//        return new ResponseEntity<>(apiError, UNAUTHORIZED);
//    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request){
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                ex.getMessage(),
                INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(apiError, INTERNAL_SERVER_ERROR);
    }


}
