package org.project.userManagement.mapper;

import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;

public class UserMapper {

    public static UserDto convertEntityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        return userDto;
    }

    public static User convertDtoToEntity(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        return user;
    }

    public static User convertCreateUserToEntity(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(createUserDto.getPassword());
        return user;
    }

    public static UserDto convertCreateUserToDto(CreateUserDto createUserDto) {
        UserDto userDto = new UserDto();
        userDto.setUsername(createUserDto.getUsername());
        return userDto;
    }

    public static UserDto convertDeleteUserToDto(DeleteUserDto deleteUserDto) {
        UserDto userDto = new UserDto();
        userDto.setUsername(deleteUserDto.getUsername());
        return userDto;
    }
}
