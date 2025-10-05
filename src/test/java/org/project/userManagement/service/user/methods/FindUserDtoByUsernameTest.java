package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class FindUserDtoByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserDtoByUsername has existent username, must return the user")
    void findUserDtoByExistentUsername() {
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.userToUserDto(user)).thenReturn(userDto);

        Assertions.assertDoesNotThrow(() -> userService.findUserDtoByUsername(username));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("When findUserDtoByUsername has nonexistent username, must throw UserNotFoundException")
    void findUserDtoByNonexistentUsername() {
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.findUserDtoByUsername(username)
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
    }
}
