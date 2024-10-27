package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.PutUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class PutUserTest extends UserServiceTest {
    @Test
    @DisplayName("When putUser has existent user, must return updated user")
    void testPutUser_HasExistentUser() {
        String newUsername = "New Username";
        String newPassword = "NewPassword123";
        String encryptedPassword = "EncryptedPassword";
        User user = new User(1L, "user@mail.tld", "User", "Password123", UserRole.USER);
        PutUserDto putUserDto = new PutUserDto(user.getEmail(), newUsername, user.getPassword(), newPassword);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(putUserDto.newPassword())).thenReturn(encryptedPassword);

        Optional<UserDto> userDtoResult = userService.putUser(putUserDto);

        Assertions.assertTrue(userDtoResult.isPresent());
        Assertions.assertEquals(newUsername, userDtoResult.get().username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(encryptedPassword, user.getPassword());
    }

    @Test
    @DisplayName("When putUser has nonexistent user, must return empty user")
    void testPutUser_HasNonexistentUser() {
        String newUsername = "New Username";
        String newPassword = "NewPassword123";
        String encryptedPassword = "EncryptedPassword";
        User user = new User(1L, "user@mail.tld", "User", "Password123", UserRole.USER);
        PutUserDto putUserDto = new PutUserDto(user.getEmail(), newUsername, user.getPassword(), newPassword);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(putUserDto.newPassword())).thenReturn(encryptedPassword);

        Optional<UserDto> userDtoResult = userService.putUser(putUserDto);

        Assertions.assertTrue(userDtoResult.isEmpty());
    }
}
