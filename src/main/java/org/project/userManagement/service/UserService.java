package org.project.userManagement.service;

import jakarta.persistence.EntityNotFoundException;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDto findUserDtoByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserDto(user.getUsername()))
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public Optional<UserDto> findUserDtoByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = userMapper.userToUserDto(user);
            return Optional.of(userDto);
        }
        return Optional.empty();
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserDto patchUser(PatchUserDto patchUserDto) {
        User user = userRepository
                .findByEmail(patchUserDto.email())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

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

    public Boolean deleteUser(DeleteUserDto deleteUserDto) {
        Optional<User> user = findUserByEmail(deleteUserDto.email());
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return !userRepository.existsByEmail(deleteUserDto.email());
        }
        return false;
    }
}
