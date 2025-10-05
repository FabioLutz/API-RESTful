package org.project.userManagement.service;

import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.RegistrationFailedException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    public UserDto registerUser(RegisterUserDto registerUserDto) {
        if (userRepository.existsByEmail(registerUserDto.email()) || userRepository.existsByUsername(registerUserDto.username())) {
            throw new RegistrationFailedException("Registration failed");
        }

        User newUser = userMapper.registerUserDtoToUser(registerUserDto);
        String encryptedPassword = passwordEncoder.encode(registerUserDto.password());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.USER);
        User user = userRepository.save(newUser);
        return userMapper.userToUserDto(user);
    }

    public UserDto findUserDtoByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(user -> userMapper.userToUserDto(user))
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserDto patchUser(PatchUserDto patchUserDto) {
        User user = userRepository
                .findByEmail(patchUserDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (patchUserDto.newPassword() != null) {
            String encryptedPassword = passwordEncoder.encode(patchUserDto.newPassword());
            user.setPassword(encryptedPassword);
        }

        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }

    public void deleteUser(DeleteUserDto deleteUserDto) {
        User user = userRepository
                .findByEmail(deleteUserDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
