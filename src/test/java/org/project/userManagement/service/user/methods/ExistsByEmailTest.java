package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.service.user.UserServiceTest;

public class ExistsByEmailTest extends UserServiceTest {
    @Test
    @DisplayName("When existsByEmail has existent email, must return true")
    void testExistsByEmail_True() {
        User user = new User(1L, "user@mail.tld", "User", "Password123", UserRole.USER);
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        boolean exists = userService.existsUserByEmail(user.getEmail());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("When existsByEmail has nonexistent email, must return false")
    void testExistsByEmail_False() {
        String nonExistentEmail = "Nonexistent Email";
        Mockito.when(userRepository.existsByEmail(nonExistentEmail)).thenReturn(false);

        boolean nonExists = userService.existsUserByEmail(nonExistentEmail);

        Assertions.assertFalse(nonExists);
    }
}
