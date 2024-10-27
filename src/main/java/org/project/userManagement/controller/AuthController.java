package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.LoginResponseDto;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.AuthService;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        if (!(userService.existsUserByEmail(registerUserDto.email()) || userService.existsUserByUsername(registerUserDto.username()))) {
            UserDto userDto = authService.registerUser(registerUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        try {
            if (userService.existsUserByEmail(loginUserDto.email())) {
                String token = authService.loginUser(loginUserDto);
                return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (BadCredentialsException badCredentialsException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
