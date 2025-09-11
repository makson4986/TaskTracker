package com.makson.tasktracker.controllers;

import com.makson.tasktracker.dto.JwtResponseDto;
import com.makson.tasktracker.dto.UserDto;
import com.makson.tasktracker.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/api/user")
    public ResponseEntity<?> singUp(@RequestBody @Validated UserDto user) {
        JwtResponseDto jwtResponseDto = authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponseDto);
    }
}
