package org.project.userManagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    User registerUserDtoToUser(RegisterUserDto registerUserDto);

    User patchUserDtoToUser(PatchUserDto patchUserDto);
}