package org.project.userManagement.service.user.methods;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class FindUserDtoByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserDtoByUsername has existent username, must return the user")
    void findUserDtoByExistentUsername() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.findUserDtoByUsername(user.getUsername()));

        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("When findUserDtoByUsername has nonexistent username, must throw EntityNotFoundException")
    void findUserDtoByNonexistentUsername() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());

        EntityNotFoundException entityNotFoundException = Assertions.assertThrows(
                EntityNotFoundException.class,
                () -> userService.findUserDtoByUsername(user.getUsername())
        );

        Assertions.assertEquals("User not found", entityNotFoundException.getMessage());
    }
}
