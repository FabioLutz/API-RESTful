package org.project.userManagement.mapper;

import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)

    User registerUserDtoToUser(RegisterUserDto registerUserDto);
}
