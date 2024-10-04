package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.*;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        Optional<UserDto> optionalUserDto = userService.findUserDtoByUsername(username);
        if (optionalUserDto.isPresent()) {
            UserDto userDto = optionalUserDto.get();
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@Valid @RequestBody CreateUserDto createUserDto) {
        if (!(userService.existsByEmail(createUserDto.email()) || userService.existsByUsername(createUserDto.username()))) {
            UserDto userDto = userService.createUser(createUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @PutMapping
    public ResponseEntity<UserDto> putUser(@Valid @RequestBody PutUserDto putUserDto) {
        if (userService.existsByUsername(putUserDto.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Optional<UserDto> optionalUserDto = userService.putUser(putUserDto);
        return optionalUserDto.map(userDto -> ResponseEntity.status(HttpStatus.OK).body(userDto)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

    @PatchMapping
    public ResponseEntity<UserDto> patchUser(@Valid @RequestBody PatchUserDto patchUserDto) {
        if (!(userService.existsByUsername(patchUserDto.username()))) {
            Optional<UserDto> optionalUserDto = userService.patchUser(patchUserDto);
            if (optionalUserDto.isPresent()) {
                UserDto userDto = optionalUserDto.get();
                return ResponseEntity.status(HttpStatus.OK).body(userDto);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deleteUser(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        if (userService.deleteUser(deleteUserDto)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}