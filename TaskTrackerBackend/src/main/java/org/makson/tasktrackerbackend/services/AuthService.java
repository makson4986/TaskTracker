package org.makson.tasktrackerbackend.services;

import org.makson.tasktrackerbackend.dto.JwtResponseDto;
import org.makson.tasktrackerbackend.dto.RegistrationDto;
import org.makson.tasktrackerbackend.exceptions.DataBaseException;
import org.makson.tasktrackerbackend.exceptions.UserAlreadyExistException;
import org.makson.tasktrackerbackend.models.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository contextRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;
    private final SecurityContextLogoutHandler securityContextLogoutHandler;

    public JwtResponseDto signUp(RegistrationDto registrationDto) {
        User user = User.builder()
                .email(registrationDto.email())
                .password(passwordEncoder.encode(registrationDto.password()))
                .build();

        User registeredUser;
        try {
            registeredUser = userService.save(user);
        } catch (Exception e) {
            if (e.getCause() instanceof ConstraintViolationException) {
                throw new UserAlreadyExistException("This email is already taken");
            }
            throw new DataBaseException(e);
        }

        return new JwtResponseDto(jwtService.generateToken(registeredUser));
    }

    public JwtResponseDto signIn(RegistrationDto registrationDto, HttpServletRequest request, HttpServletResponse response) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(registrationDto.email(), registrationDto.password()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authenticate);
        SecurityContextHolder.setContext(context);
        contextRepository.saveContext(context, request, response);

        User user = (User) authenticate.getPrincipal();
        return new JwtResponseDto(jwtService.generateToken(user));
    }

    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        securityContextLogoutHandler.logout(request, response, authentication);
    }
}
