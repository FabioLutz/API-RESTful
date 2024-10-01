package org.project.userManagement.controllers;

import org.project.userManagement.dto.UserDto;
import org.project.userManagement.models.User;
import org.project.userManagement.services.UserService;
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
    public ResponseEntity<UserDto> postUser(@RequestBody User user) {
        UserDto userDto = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }
}
