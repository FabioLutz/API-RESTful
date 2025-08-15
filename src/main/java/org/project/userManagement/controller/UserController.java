package org.project.userManagement.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
        try {
            UserDto userDto = userService.findUserDtoByUsername(username);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping
    public ResponseEntity<UserDto> patchUser(@Valid @RequestBody PatchUserDto patchUserDto) {
        try {
            UserDto userDto = userService.patchUser(patchUserDto);
            return ResponseEntity.ok(userDto);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.unprocessableEntity().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping
    public ResponseEntity<UserDto> deleteUser(@Valid @RequestBody DeleteUserDto deleteUserDto) {
        if (userService.deleteUser(deleteUserDto)) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
