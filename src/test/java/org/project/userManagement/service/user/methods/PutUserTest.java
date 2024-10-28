package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.PutUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class PutUserTest extends UserServiceTest {
    private PutUserDto putUserDto;

    @BeforeEach
    protected void setPutUserDto() {
        putUserDto = new PutUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
    }

    @Test
    @DisplayName("When putUser has existent user, must return updated user")
    void putExistentUser() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(putUserDto.newPassword())).thenReturn(encryptedPassword);

        Optional<UserDto> userDtoResult = userService.putUser(putUserDto);

        Assertions.assertTrue(userDtoResult.isPresent());
        Assertions.assertEquals(newUsername, userDtoResult.get().username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("When putUser has nonexistent user, must return empty user")
    void putNonexistentUser() {
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(passwordEncoder.encode(putUserDto.newPassword())).thenReturn(encryptedPassword);

        Optional<UserDto> userDtoResult = userService.putUser(putUserDto);

        Assertions.assertTrue(userDtoResult.isEmpty());
    }
}
