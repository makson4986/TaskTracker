package org.makson.tasktrackerbackend.controllers;

import org.makson.tasktrackerbackend.dto.ErrorDto;
import org.makson.tasktrackerbackend.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<?> handleBadRequestException(Exception ex) {
        ErrorDto errorDto;

        if (ex instanceof MethodArgumentNotValidException) {
            errorDto = new ErrorDto(
                    ((MethodArgumentNotValidException) ex).getFieldErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList()
                            .getFirst()
            );
        } else if (ex instanceof MethodArgumentTypeMismatchException) {
            errorDto = new ErrorDto("The ID is incorrect");
        } else {
            errorDto = new ErrorDto(ex.getMessage());
        }

        log.warn(errorDto.message());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler({
            JwtClaimsException.class,
            InvalidJwtException.class,
            JwtExtractionException.class
    })
    public ResponseEntity<?> handleAuthenticationException(Exception ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleConflictException(Exception ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<?> handleNotFoundException(Exception ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ErrorDto(ex.getMessage()));
    }

}
