package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class FindUserDtoByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserDtoByUsername has existent username, must return the user")
    void testFindUserDtoByUsername_HasExistentUsername() {
        User user = new User(1L, "user@mail.tld", "User", "Password123");
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<UserDto> optionalUserDto = userService.findUserDtoByUsername(user.getUsername());

        Assertions.assertTrue(optionalUserDto.isPresent());
        Assertions.assertEquals(user.getUsername(), optionalUserDto.get().username());
    }

    @Test
    @DisplayName("When findUserDtoByUsername has nonexistent username, must return optional empty")
    void testFindUserDtoByUsername_HasNonexistentUsername() {
        String nonExistentUsername = "Nonexistent Username";
        Mockito.when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        Optional<UserDto> optionalUserDto = userService.findUserDtoByUsername(nonExistentUsername);

        Assertions.assertTrue(optionalUserDto.isEmpty());
    }
}
