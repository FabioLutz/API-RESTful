package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.ChangePasswordDto;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto) {
        UserDto userDto = userService.registerUser(registerUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.findUserDtoByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto) {
        UserDto userDto = userService.changePassword(changePasswordDto);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<UserDto> deleteUser(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        userService.deleteUser(deleteUserDto);
        return ResponseEntity.noContent().build();
    }
}
