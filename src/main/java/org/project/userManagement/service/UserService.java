package org.project.userManagement.service;

import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.project.userManagement.mapper.UserMapper.convertCreateUserToEntity;
import static org.project.userManagement.mapper.UserMapper.convertEntityToDto;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<UserDto> findUserDtoByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserDto userDto = convertEntityToDto(user);
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Boolean existsByUsername(String username) {
        return findUserDtoByUsername(username).isPresent();
    }

    public UserDto createUserDto(CreateUserDto createUserDto) {
        return convertEntityToDto(userRepository.save(convertCreateUserToEntity(createUserDto)));
    }

    public Boolean deleteUser(DeleteUserDto deleteUserDto) {
        Optional<User> user = findUserByUsername(deleteUserDto.getUsername());
        if (user.isPresent()) {
            userRepository.deleteById(user.get().getId());
            return true;
        }
        return false;
    }
}