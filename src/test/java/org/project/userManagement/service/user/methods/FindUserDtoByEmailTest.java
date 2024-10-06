package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class FindUserDtoByEmailTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserDtoByEmail has existent email, must return the user")
    void testFindUserDtoByEmail_HasExistentEmail() {
        User user = new User(1L, "user@mail.tld", "User", "Password123");
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<UserDto> optionalUserDto = userService.findUserDtoByEmail(user.getEmail());

        Assertions.assertTrue(optionalUserDto.isPresent());
        Assertions.assertEquals(user.getUsername(), optionalUserDto.get().username());
    }

    @Test
    @DisplayName("When findUserDtoByEmail has nonexistent email, must return optional empty")
    void testFindUserDtoByEmail_HasNonexistentEmail() {
        String nonExistentEmail = "Nonexistent Email";
        Mockito.when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        Optional<UserDto> optionalUserDto = userService.findUserDtoByEmail(nonExistentEmail);

        Assertions.assertTrue(optionalUserDto.isEmpty());
    }
}
