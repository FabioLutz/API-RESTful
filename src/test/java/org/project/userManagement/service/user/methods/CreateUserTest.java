package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;

public class CreateUserTest extends UserServiceTest {
    @Test
    @DisplayName("When createUser has new user, must create user")
    void testCreateUser() {
        String username = "User";
        User user = new User(1L, "user@mail.tld", username, "Password123");
        CreateUserDto createUserDto = new CreateUserDto(user.getEmail(), user.getUsername(), user.getPassword());
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        UserDto userDtoResult = userService.createUser(createUserDto);

        Assertions.assertNotNull(userDtoResult);
        Assertions.assertEquals(username, userDtoResult.username());
    }
}
