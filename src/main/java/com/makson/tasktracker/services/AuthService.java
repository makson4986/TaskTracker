package com.makson.tasktracker.services;

import com.makson.tasktracker.dto.JwtResponseDto;
import com.makson.tasktracker.dto.UserDto;
import com.makson.tasktracker.exceptions.DataBaseException;
import com.makson.tasktracker.exceptions.UserAlreadyExistException;
import com.makson.tasktracker.models.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    public JwtResponseDto signUp(UserDto userDto) {
        User user = User.builder()
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
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

        String jwt = jwtService.generateToken(registeredUser);
        return new JwtResponseDto(jwt);
    }
}
