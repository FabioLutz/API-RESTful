package org.project.userManagement.service;

import lombok.RequiredArgsConstructor;
import org.project.userManagement.dto.ChangePasswordDto;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.RegistrationFailedException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    public UserDto changePassword(ChangePasswordDto changePasswordDto) {
        User user = userRepository
                .findByEmail(changePasswordDto.email())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        String encryptedPassword = passwordEncoder.encode(changePasswordDto.newPassword());
        user.setPassword(encryptedPassword);

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
