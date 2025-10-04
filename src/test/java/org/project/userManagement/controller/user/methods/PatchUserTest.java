package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.NoUpdateProvidedException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.exception.UsernameAlreadyExistsException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PatchUserTest extends UserControllerTest {
    private final String email = "user@mail.tld";
    private final String password = "Password123";
    private final String newPassword = "NewPassword123";
    private final String newUsername = "New username";
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
        UserDto userDto = new UserDto(newUsername);

        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(newUsername));
    }

    @Test
    @DisplayName("When Patch User with duplicate username, must return status 409")
    @WithMockUser
    void patchDuplicateUsername() throws Exception {
        String username = "User";
        patchUserDto = new PatchUserDto(
                email,
                username,
                password,
                null
        );

        Mockito.doThrow(new UsernameAlreadyExistsException("Username already exists"))
                .when(userService).patchUser(patchUserDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
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

        Mockito.doThrow(new NoUpdateProvidedException("No fields to update"))
                .when(userService).patchUser(patchUserDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
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

        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).patchUser(patchUserDto);

        mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patchUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
