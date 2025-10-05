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
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.findUserDtoByUsername(user.getUsername()));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("When findUserDtoByUsername has nonexistent username, must throw UserNotFoundException")
    void findUserDtoByNonexistentUsername() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.findUserDtoByUsername(user.getUsername())
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
    }
}
