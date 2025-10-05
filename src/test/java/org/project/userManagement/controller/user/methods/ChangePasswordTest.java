package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.ChangePasswordDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class ChangePasswordTest extends UserControllerTest {
    private ChangePasswordDto changePasswordDto;

    @BeforeEach
    protected void setChangePasswordDto() {
        changePasswordDto = new ChangePasswordDto(
                "user@mail.tld",
                "Password123",
                "NewPassword123"
        );
    }

    @Test
    @DisplayName("When Change Password with existent User, must return new username, then status 200")
    @WithMockUser
    void changePasswordWithExistentUser() throws Exception {
        String username = "username";
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.changePassword(changePasswordDto)).willReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Change Password with nonexistent User, must return status 404")
    @WithMockUser
    void changePasswordWithNonexistentUser() throws Exception {
        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).changePassword(changePasswordDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
