package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DeleteUserTest extends UserServiceTest {
    private DeleteUserDto deleteUserDto;

    @BeforeEach
    protected void setDeleteUserDto() {
        deleteUserDto = new DeleteUserDto(
                email,
                password
        );
    }

    @Test
    @DisplayName("When deleteUser has existent user, must delete user")
    void deleteExistentUser() {
        Mockito.when(userRepository.findByEmail(deleteUserDto.email())).thenReturn(Optional.of(user));

        Assertions.assertDoesNotThrow(() -> userService.deleteUser(deleteUserDto));

        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    @DisplayName("When deleteUser has nonexistent user, must throw UserNotFoundException")
    void deleteNonexistentUser() {
        Mockito.when(userRepository.findByEmail(deleteUserDto.email())).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.deleteUser(deleteUserDto)
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).delete(user);
    }
}
