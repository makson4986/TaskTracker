package com.makson.tasktracker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makson.tasktracker.dto.JwtResponseDto;
import com.makson.tasktracker.dto.RegistrationDto;
import com.makson.tasktracker.dto.UserDto;
import com.makson.tasktracker.models.User;
import com.makson.tasktracker.services.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final ObjectMapper objectMapper;

    @PostMapping("/user")
    public ResponseEntity<?> singUp(@RequestBody @Validated RegistrationDto user) {
        JwtResponseDto jwtResponseDto = authService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(jwtResponseDto);
    }

    @PostMapping("auth/login")
    public ResponseEntity<?> signIn(@RequestBody @Validated RegistrationDto user, HttpServletRequest request, HttpServletResponse response) {
        JwtResponseDto jwtResponseDto = authService.signIn(user, request, response);
        return ResponseEntity.ok(jwtResponseDto);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response,  Authentication authentication) {
        authService.logout(request, response, authentication);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCurrentUser(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(objectMapper.convertValue(user, UserDto.class));
    }
}
