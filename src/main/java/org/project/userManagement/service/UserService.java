package org.project.userManagement.service;

import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.PutUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Optional<UserDto> findUserDtoByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = userMapper.userToUserDto(user);
            return Optional.of(userDto);
        }
        return Optional.empty();
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

    public Optional<UserDto> putUser(PutUserDto putUserDto) {
        Optional<User> optionalUser = findUserByEmail(putUserDto.email());
        if (optionalUser.isPresent()) {
            optionalUser.get().setUsername(putUserDto.username());
            String encryptedPassword = passwordEncoder.encode(putUserDto.newPassword());
            optionalUser.get().setPassword(encryptedPassword);
            User user = userRepository.save(optionalUser.get());
            return Optional.of(userMapper.userToUserDto(user));
        }
        return Optional.empty();
    }

    public Optional<UserDto> patchUser(PatchUserDto patchUserDto) {
        Optional<User> optionalUser = findUserByEmail(patchUserDto.email());
        if (optionalUser.isPresent()) {
            boolean isUpdated = false;
            if (patchUserDto.username() != null) {
                optionalUser.get().setUsername(patchUserDto.username());
                isUpdated = true;
            }

            if (patchUserDto.newPassword() != null) {
                String encryptedPassword = passwordEncoder.encode(patchUserDto.newPassword());
                optionalUser.get().setPassword(encryptedPassword);
                isUpdated = true;
            }

            if (isUpdated) {
                User user = userRepository.save(optionalUser.get());
                return Optional.of(userMapper.userToUserDto(user));
            }
        }
        return Optional.empty();
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