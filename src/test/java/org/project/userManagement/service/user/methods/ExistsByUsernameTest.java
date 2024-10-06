package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

public class ExistsByUsernameTest extends UserServiceTest {
    @Test
    @DisplayName("When existsByUsername has existent username, must return true")
    void testExistsByUsername_True() {
        User user = new User(1L, "user@mail.tld", "User", "Password123");
        Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);

        boolean exists = userService.existsUserByUsername(user.getUsername());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("When existsByUsername has nonexistent username, must return false")
    void testExistsByUsername_False() {
        String nonExistentUsername = "Nonexistent Username";
        Mockito.when(userRepository.existsByUsername(nonExistentUsername)).thenReturn(false);

        boolean nonExists = userService.existsUserByUsername(nonExistentUsername);

        Assertions.assertFalse(nonExists);
    }
}
