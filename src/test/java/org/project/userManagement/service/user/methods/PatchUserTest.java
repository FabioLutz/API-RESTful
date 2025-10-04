package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.NoUpdateProvidedException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.exception.UsernameAlreadyExistsException;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PatchUserTest extends UserServiceTest {
    private PatchUserDto patchUserDto;

    @Test
    @DisplayName("When patchUser has new username, must return updated user")
    void patchNewUsername() {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                null
        );
        Mockito.when(userRepository.findByEmail(patchUserDto.email())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto userDtoResult = Assertions.assertDoesNotThrow(() -> userService.patchUser(patchUserDto));

        Mockito.verify(userRepository).save(user);

        Assertions.assertEquals(newUsername, userDtoResult.username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has new password, must return updated user")
    void patchNewPassword() {
        patchUserDto = new PatchUserDto(
                email,
                null,
                password,
                newPassword
        );
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
    @DisplayName("When patchUser has new username and new password, must return updated user")
    void patchNewUsernameAndNewPassword() {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(patchUserDto.newPassword())).thenReturn(encryptedPassword);

        UserDto userDtoResult = Assertions.assertDoesNotThrow(() -> userService.patchUser(patchUserDto));

        Mockito.verify(userRepository).save(user);

        Assertions.assertEquals(newUsername, userDtoResult.username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has an existing username, must throw UsernameAlreadyExistsException")
    void patchExistingUsername() {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                null
        );
        Mockito.when(userRepository.findByEmail(patchUserDto.email())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.existsByUsername(patchUserDto.username())).thenReturn(true);

        UsernameAlreadyExistsException usernameAlreadyExistsException = Assertions.assertThrows(
                UsernameAlreadyExistsException.class,
                () -> userService.patchUser(patchUserDto)
        );

        Assertions.assertEquals("Username already exists", usernameAlreadyExistsException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    @DisplayName("When patchUser has no new values, must throw IllegalArgumentException")
    void patchNoValues() {
        patchUserDto = new PatchUserDto(
                email,
                null,
                password,
                null
        );
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        NoUpdateProvidedException noUpdateProvidedException = Assertions.assertThrows(
                NoUpdateProvidedException.class,
                () -> userService.patchUser(patchUserDto)
        );

        Assertions.assertEquals("No fields to update", noUpdateProvidedException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }

    @Test
    @DisplayName("When patchUser has nonexistent user, must throw UserNotFoundException")
    void patchNonexistentUser() {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.patchUser(patchUserDto)
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }
}
