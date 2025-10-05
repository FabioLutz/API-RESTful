package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PatchUserTest extends UserControllerTest {
    private final String email = "user@mail.tld";
    private final String password = "Password123";
    private final String newPassword = "NewPassword123";
    private PatchUserDto patchUserDto;

    @Test
    @DisplayName("When Patch User with new data, must return new username, then status 200")
    @WithMockUser
    void patchUserAndNewData() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                password,
                newPassword
        );
        String username = "Username";
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Patch nonexistent User, must return status 404")
    @WithMockUser
    void patchNonexistentUser() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                password,
                newPassword
        );

        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).patchUser(patchUserDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
