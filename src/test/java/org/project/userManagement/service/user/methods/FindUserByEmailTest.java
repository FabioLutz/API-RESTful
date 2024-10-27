package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.service.user.UserServiceTest;

import java.util.Optional;

public class FindUserByEmailTest extends UserServiceTest {
    @Test
    @DisplayName("When findUserByEmail has existent email, must return the user")
    void testFindUserByEmail_HasExistentEmail() {
        User user = new User(1L, "user@mail.tld", "User", "Password123", UserRole.USER);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<User> optionalUser = userService.findUserByEmail(user.getEmail());

        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals(user.getId(), optionalUser.get().getId());
        Assertions.assertEquals(user.getEmail(), optionalUser.get().getEmail());
        Assertions.assertEquals(user.getUsername(), optionalUser.get().getUsername());
        Assertions.assertEquals(user.getPassword(), optionalUser.get().getPassword());
    }

    @Test
    @DisplayName("When findUserByEmail has nonexistent email, must return empty user")
    void testFindUserByEmail_HasNonExistentEmail() {
        String nonExistentEmail = "Nonexistent Email";
        Mockito.when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        Optional<User> optionalUser = userService.findUserByEmail(nonExistentEmail);

        Assertions.assertTrue(optionalUser.isEmpty());
    }
}
