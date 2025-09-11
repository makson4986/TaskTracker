package com.makson.tasktracker.controllers;

import com.makson.tasktracker.dto.JwtResponseDto;
import com.makson.tasktracker.dto.UserDto;
import com.makson.tasktracker.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/user")
    public ResponseEntity<?> singUp(@RequestBody @Validated UserDto user) {
        JwtResponseDto jwtResponseDto = authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponseDto);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> signIn(@RequestBody @Validated UserDto user, HttpServletRequest request, HttpServletResponse response) {
        JwtResponseDto jwtResponseDto = authService.signIn(user, request, response);
        return ResponseEntity.ok(jwtResponseDto);
    }

}
