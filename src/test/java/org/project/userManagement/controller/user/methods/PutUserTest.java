package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PutUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class PutUserTest extends UserControllerTest {
    private final String username = "User";
    private final String newUsername = "New Username";
    private final String email = "user@mail.tld";
    private final String password = "Password123";
    private final String newPassword = "NewPassword123";
    private PutUserDto putUserDto;
    private UserDto userDto;

    @Test
    @DisplayName("When Put existent User with new data, must return new username, then status 200")
    @WithMockUser
    void putUser() throws Exception {
        putUserDto = new PutUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(newUsername));
    }

    @Test
    @DisplayName("When Put existent User with existent username, must return status 409")
    @WithMockUser
    void putDuplicateUsername() throws Exception {
        putUserDto = new PutUserDto(
                email,
                username,
                password,
                newPassword
        );
        userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(true);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        BDDMockito.verify(userService, Mockito.never()).putUser(putUserDto);
        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("When Put nonexistent User, must return status 404")
    @WithMockUser
    void putNonexistentUser() throws Exception {
        putUserDto = new PutUserDto(
                email,
                newUsername,
                password,
                newPassword
        );

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("When Put User by unauthenticated User, must return status 403")
    void putUnauthenticatedUser() throws Exception {
        putUserDto = new PutUserDto(
                email,
                newUsername,
                password,
                newPassword
        );
        userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        BDDMockito.verify(userService, Mockito.never()).existsUserByUsername(username);
        BDDMockito.verify(userService, Mockito.never()).putUser(putUserDto);
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
