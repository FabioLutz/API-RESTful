package org.project.userManagement.mapper;

import org.mapstruct.*;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User registerUserDtoToUser(RegisterUserDto registerUserDto, @Context PasswordEncoder passwordEncoder);

    @AfterMapping
    default void encryptPassword(RegisterUserDto dto, @MappingTarget User user, @Context PasswordEncoder passwordEncoder) {
        user.setPassword(passwordEncoder.encode(dto.password()));
    }
}
