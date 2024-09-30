package org.project.userManagement.services;

import org.project.userManagement.dto.UserDto;
import org.project.userManagement.models.User;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private static UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public Optional<UserDto> findUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        UserDto userDto;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userDto = convertToDto(user);
            return Optional.of(userDto);
        } else {
            return Optional.empty();
        }
    }
}
