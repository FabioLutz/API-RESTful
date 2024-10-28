package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class FindUserByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserByUsername has existent username, must return the user")
    void findUserByExistentUsername() {
        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userService.findUserByUsername(user.getUsername());

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals(user.getId(), optionalUser.get().getId());
        Assertions.assertEquals(user.getEmail(), optionalUser.get().getEmail());
        Assertions.assertEquals(user.getUsername(), optionalUser.get().getUsername());
        Assertions.assertEquals(user.getPassword(), optionalUser.get().getPassword());
    }

    @Test
    @DisplayName("When findUserByUsername has nonexistent username, must return the user")
    void findUserByNonexistentUsername() {
        String nonExistentUsername = "Nonexistent Username";
        Mockito.when(userRepository.findByUsername(nonExistentUsername)).thenReturn(Optional.empty());

        Optional<User> optionalUser = userService.findUserByUsername(nonExistentUsername);

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
