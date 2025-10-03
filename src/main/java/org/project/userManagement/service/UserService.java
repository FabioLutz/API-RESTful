package org.project.userManagement.service;

import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto findUserDtoByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(user -> new UserDto(user.getUsername()))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDto patchUser(PatchUserDto patchUserDto) {
        User user = userRepository
                .findByEmail(patchUserDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean isUpdated = false;
        if (patchUserDto.username() != null) {
            if (userRepository.existsByUsername(patchUserDto.username())) {
                throw new DataIntegrityViolationException("Username already exists");
            }
            user.setUsername(patchUserDto.username());
            isUpdated = true;
        }

        if (patchUserDto.newPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(patchUserDto.newPassword());
            user.setPassword(encryptedPassword);
            isUpdated = true;
        }

        if (!isUpdated) {
            throw new IllegalArgumentException("No fields to update");
        }

        user = userRepository.save(user);
        return new UserDto(user.getUsername());
    }

    public void deleteUser(DeleteUserDto deleteUserDto) {
        User user = userRepository
                .findByEmail(deleteUserDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
