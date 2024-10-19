package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.AuthService;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody CreateUserDto createUserDto) {
        if (!(userService.existsUserByEmail(createUserDto.email()) || userService.existsUserByUsername(createUserDto.username()))) {
            UserDto userDto = userService.createUser(createUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        Optional<UserDto> userDto = userService.findUserDtoByEmail(loginUserDto.email());
        if (userDto.isPresent()) {
            if (authService.verify(loginUserDto)) {
                return ResponseEntity.status(HttpStatus.OK).body(userDto.get());
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
