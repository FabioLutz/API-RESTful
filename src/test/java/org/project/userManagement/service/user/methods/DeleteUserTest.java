package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

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
    @DisplayName("When deleteUser has existent user, return true")
    void deleteExistentUser() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.doNothing().when(userRepository).delete(user);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        boolean deleted = userService.deleteUser(deleteUserDto);

        Assertions.assertTrue(deleted);
    }

    @Test
    @DisplayName("When deleteUser has nonexistent user, return false")
    void deleteNonexistentUser() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.doNothing().when(userRepository).delete(user);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(false);

        boolean deleted = userService.deleteUser(deleteUserDto);

        Assertions.assertFalse(deleted);
    }
}
