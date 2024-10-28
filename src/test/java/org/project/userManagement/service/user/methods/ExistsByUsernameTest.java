package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.service.user.UserServiceTest;

public class ExistsByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When existsByUsername has existent username, must return true")
    void existsByUsernameExistentUser() {
        User user = new User(1L, "user@mail.tld", "User", "Password123", UserRole.USER);
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        boolean exists = userService.existsUserByUsername(user.getUsername());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("When existsByUsername has nonexistent username, must return false")
    void existsByUsernameNonexistentUser() {
        String nonExistentUsername = "Nonexistent Username";
        Mockito.when(userRepository.existsByUsername(nonExistentUsername)).thenReturn(false);

        boolean exists = userService.existsUserByUsername(nonExistentUsername);

        Assertions.assertFalse(exists);
    }
}
