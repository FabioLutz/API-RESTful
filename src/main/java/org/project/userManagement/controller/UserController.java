package org.project.userManagement.controller;

import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
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
        Optional<UserDto> userDto = userService.findUserByUsername(username);
        return userDto.map(dto -> ResponseEntity.status(HttpStatus.OK).body(dto)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping
    public ResponseEntity<UserDto> postUser(@RequestBody CreateUserDto createUserDto) {
        if (!(userService.existsByUsername(createUserDto.getUsername()))) {
            UserDto userDto = userService.createUser(createUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
        } else {
            UserDto userDto = UserMapper.convertCreateUserToDto(createUserDto);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(userDto);
        }
    }
}
