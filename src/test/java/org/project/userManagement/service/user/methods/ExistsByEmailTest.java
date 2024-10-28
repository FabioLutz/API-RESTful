package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.service.user.UserServiceTest;

public class ExistsByEmailTest extends UserServiceTest {
    @Test
    @DisplayName("When existsByEmail has existent email, must return true")
    void existsByEmailExistentUser() {
        Mockito.when(userRepository.existsByEmail(user.getEmail())).thenReturn(true);

        boolean exists = userService.existsUserByEmail(user.getEmail());

        Assertions.assertTrue(exists);
    }

    @Test
    @DisplayName("When existsByEmail has nonexistent email, must return false")
    void existsByEmailNonexistentUser() {
        String nonExistentEmail = "Nonexistent Email";
        Mockito.when(userRepository.existsByEmail(nonExistentEmail)).thenReturn(false);

        boolean exists = userService.existsUserByEmail(nonExistentEmail);

        Assertions.assertFalse(exists);
    }
}
