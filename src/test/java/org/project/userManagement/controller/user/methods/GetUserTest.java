package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.UserDto;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class GetUserTest extends UserControllerTest {
    private final String username = "User";
    private UserDto userDto;

    @BeforeEach
    protected void setUserDto() {
        userDto = new UserDto(username);
    }

    @Test
    @DisplayName("when Get valid User, must return username, then status 200")
    @WithMockUser
    void getValidUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByUsername(username)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/profile/{username}", username));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Get invalid User, must return status 404")
    @WithMockUser
    void getInvalidUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByUsername(username)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/profile/{username}", username));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("When Get User by unauthenticated User, must return status 403")
    void getUnauthenticatedUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByUsername(username)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/profile/{username}", username));

        BDDMockito.verify(userService, Mockito.never()).findUserDtoByUsername(username);
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
