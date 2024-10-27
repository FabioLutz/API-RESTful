package org.project.userManagement.service;

import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public boolean verify(LoginUserDto loginUserDto) {
        Optional<User> user = userRepository.findByEmail(loginUserDto.email());
        return user.map(value -> value.getPassword().equals(loginUserDto.password())).orElse(false);
    }

    public UserDto registerUser(RegisterUserDto registerUserDto) {
        User user = userMapper.registerUserDtoToUser(registerUserDto);
        user = userRepository.save(user);
        return userMapper.userToUserDto(user);
    }
}
