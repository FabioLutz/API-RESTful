package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatchUserTest extends UserServiceTest {
    private PatchUserDto patchUserDto;

    @BeforeEach
    protected void setPatchUserDto() {
        patchUserDto = new PatchUserDto(
                email,
                password,
                newPassword
        );
    }

    @Test
    @DisplayName("When patchUser has new password, must return updated user")
    void patchNewPassword() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(patchUserDto.newPassword())).thenReturn(encryptedPassword);

        UserDto userDto = Assertions.assertDoesNotThrow(() -> userService.patchUser(patchUserDto));

        Mockito.verify(userRepository).save(user);

        Assertions.assertEquals(username, userDto.username());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has nonexistent user, must throw UserNotFoundException")
    void patchNonexistentUser() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.patchUser(patchUserDto)
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }
}
