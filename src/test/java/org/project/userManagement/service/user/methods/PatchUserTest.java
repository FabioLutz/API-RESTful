package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class PatchUserTest extends UserServiceTest {
    @Test
    @DisplayName("When patchUser has new username, must return updated user")
    void testPatchUser_HasNewUsername() {
        String newUsername = "New Username";
        String password = "Password123";
        User user = new User(1L, "user@mail.tld", "User", password, UserRole.USER);
        PatchUserDto patchUserDto = new PatchUserDto(user.getEmail(), newUsername, password, null);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserDto> userDtoResult = userService.patchUser(patchUserDto);

        Assertions.assertTrue(userDtoResult.isPresent());
        Assertions.assertEquals(newUsername, userDtoResult.get().username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has new password, must return updated user")
    void testPatchUser_HasNewPassword() {
        String newPassword = "New Password";
        String username = "User";
        User user = new User(1L, "user@mail.tld", username, "Password123", UserRole.USER);
        PatchUserDto patchUserDto = new PatchUserDto(user.getEmail(), null, user.getPassword(), newPassword);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserDto> userDtoResult = userService.patchUser(patchUserDto);

        Assertions.assertTrue(userDtoResult.isPresent());
        Assertions.assertEquals(newPassword, user.getPassword());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(username, userDtoResult.get().username());
    }

    @Test
    @DisplayName("When patchUser has new username and new password, must return updated user")
    void testPatchUser_HasNewUsernameAndNewPassword() {
        String username = "User";
        String password = "Password123";
        String newUsername = "New Username";
        String newPassword = "New Password";
        User user = new User(1L, "user@mail.tld", username, password, UserRole.USER);
        PatchUserDto patchUserDto = new PatchUserDto(user.getEmail(), newUsername, user.getPassword(), newPassword);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserDto> userDtoResult = userService.patchUser(patchUserDto);

        Assertions.assertTrue(userDtoResult.isPresent());
        Assertions.assertEquals(newUsername, userDtoResult.get().username());
        Assertions.assertEquals(newUsername, user.getUsername());
        Assertions.assertEquals(newPassword, user.getPassword());
        Assertions.assertNotEquals(username, user.getPassword());
        Assertions.assertNotEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has no new values, must return empty user")
    void testPatchUser_HasNoNewValues() {
        String username = "User";
        String password = "Password123";
        User user = new User(1L, "user@mail.tld", username, password, UserRole.USER);
        PatchUserDto patchUserDto = new PatchUserDto(user.getEmail(), null, user.getPassword(), null);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserDto> userDtoResult = userService.patchUser(patchUserDto);

        Assertions.assertTrue(userDtoResult.isEmpty());
        Assertions.assertEquals(username, user.getUsername());
        Assertions.assertEquals(password, user.getPassword());
    }

    @Test
    @DisplayName("When patchUser has nonexistent user, must return empty user")
    void testPatchUser_HasNonexistentUser() {
        String username = "User";
        String password = "Password123";
        String newUsername = "New Username";
        String newPassword = "New Password";
        User user = new User(1L, "user@mail.tld", username, password, UserRole.USER);
        PatchUserDto patchUserDto = new PatchUserDto(user.getEmail(), newUsername, user.getPassword(), newPassword);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        Optional<UserDto> userDtoResult = userService.patchUser(patchUserDto);

        Assertions.assertTrue(userDtoResult.isEmpty());
    }
}
