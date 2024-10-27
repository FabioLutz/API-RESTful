package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class PatchUserTest extends UserControllerTest {
    private final String email = "user@mail.tld";
    private final String username = "User";
    private final String password = "Password123";
    private final String newPassword = "NewPassword123";
    private final String newUsername = "New username";

    private UserDto userDto;
    private PatchUserDto patchUserDto;

    @Test
    @DisplayName("When Patch User with new data, must return new username, then status 200")
    @WithMockUser
    void patchUserAndNewData() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByEmail(email)).willReturn(true);
        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(newUsername));
    }

    @Test
    @DisplayName("When Patch User with duplicate username, must return status 409")
    @WithMockUser
    void patchDuplicateUsername() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                username,
                password,
                null
        );
        userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByEmail(email)).willReturn(true);
        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(true);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        BDDMockito.verify(userService, Mockito.never()).patchUser(patchUserDto);
        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("When Patch without username and password, must return status 422")
    @WithMockUser
    void patchWithoutNewData() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                null,
                password,
                null
        );

        BDDMockito.given(userService.existsUserByEmail(email)).willReturn(true);
        BDDMockito.given(userService.existsUserByUsername(null)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("When Patch nonexistent User, must return status 404")
    @WithMockUser
    void patchNonexistentUser() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                newPassword
        );

        BDDMockito.given(userService.existsUserByEmail(email)).willReturn(false);
        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("When Patch User by unauthenticated User, must return status 403")
    void patchUnauthenticatedUser() throws Exception {
        patchUserDto = new PatchUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByEmail(email)).willReturn(false);
        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        BDDMockito.verify(userService, Mockito.never()).existsUserByUsername(username);
        BDDMockito.verify(userService, Mockito.never()).patchUser(patchUserDto);
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
