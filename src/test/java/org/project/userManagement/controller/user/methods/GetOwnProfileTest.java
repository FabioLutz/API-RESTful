package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class GetOwnProfileTest extends UserControllerTest {

    @Test
    @DisplayName("When Get own profile with valid user, must return user profile, then status 200")
    void getOwnProfileValidUser() throws Exception {
        String username = "User";
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.getOwnProfile())
                .willReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Get own profile with nonexistent user, must return status 404")
    void getOwnProfileNonexistentUser() throws Exception {
        BDDMockito.given(userService.getOwnProfile())
                .willThrow(new UserNotFoundException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/profile"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
