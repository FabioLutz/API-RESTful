package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.LoginResponseDto;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> loginUser(@Valid @RequestBody LoginUserDto loginUserDto) {
        String token = authService.loginUser(loginUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(new LoginResponseDto(token));
    }
}
