package com.makson.tasktracker.controllers;

import com.makson.tasktracker.dto.ErrorDto;
import com.makson.tasktracker.exceptions.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandlerController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleBadRequestException(Exception ex) {
        ErrorDto errorDto;

        if (ex instanceof MethodArgumentNotValidException) {
            errorDto = new ErrorDto(
                    ((MethodArgumentNotValidException) ex).getFieldErrors().stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)
                            .toList()
                            .getFirst()
            );
        } else {
            errorDto = new ErrorDto(ex.getMessage());
        }

        log.warn(errorDto.message());
        return ResponseEntity.badRequest().body(errorDto);
    }


    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<?> handleConflictException(Exception ex) {
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleInternalServerException(Exception ex) {
        log.error(ex.getMessage(), ex);
        return ResponseEntity.internalServerError().body(new ErrorDto(ex.getMessage()));
    }

}
