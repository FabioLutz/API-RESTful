package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.ChangePasswordDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class ChangePasswordTest extends UserServiceTest {
    private ChangePasswordDto changePasswordDto;

    @BeforeEach
    protected void setChangePasswordDto() {
        changePasswordDto = new ChangePasswordDto(
                email,
                password,
                newPassword
        );
    }

    @Test
    @DisplayName("When changePassword has existent User, must return updated user")
    void changePasswordWithExistentUser() {
        Mockito.when(userRepository.findByEmail(changePasswordDto.email())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(changePasswordDto.newPassword())).thenReturn(encryptedPassword);
        Mockito.when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto dtoResult = Assertions.assertDoesNotThrow(() -> userService.changePassword(changePasswordDto));

        Mockito.verify(userRepository).save(user);

        Assertions.assertEquals(username, dtoResult.username());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("When changePassword has nonexistent User, must throw UserNotFoundException")
    void changePasswordWithNonexistentUser() {
        Mockito.when(userRepository.findByEmail(changePasswordDto.email())).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.changePassword(changePasswordDto)
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
        Mockito.verify(userRepository, Mockito.never()).save(user);
    }
}
