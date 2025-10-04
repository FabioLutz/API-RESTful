package org.project.userManagement.controller;

import jakarta.validation.Valid;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        UserDto userDto = userService.findUserDtoByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchUser(@Valid @RequestBody PatchUserDto patchUserDto) {
        try {
            UserDto userDto = userService.patchUser(patchUserDto);
            return ResponseEntity.ok(userDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deleteUser(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        userService.deleteUser(deleteUserDto);
        return ResponseEntity.noContent().build();
    }
}
