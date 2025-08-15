package org.project.userManagement.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.project.userManagement.dto.LoginResponseDto;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.AuthService;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        try {
            UserDto userDto = authService.registerUser(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        try {
            String token = authService.loginUser(loginUserDto);
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
