package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.UserDto;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class GetUserTest extends UserControllerTest {
    @Test
    @DisplayName("when Get valid User, must return username, then status 201")
    void getValidUser() throws Exception {
        String username = "User";

        UserDto userDto = new UserDto(username);
        BDDMockito.given(userService.findUserDtoByUsername(username)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/profile/{username}", username));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User"));
    }

    @Test
    @DisplayName("When Get invalid User, must return status 404")
    void getInvalidUser() throws Exception {
        String username = "User";

        BDDMockito.given(userService.findUserDtoByUsername(username)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/profile/{username}", username));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
