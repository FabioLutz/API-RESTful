package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PatchUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class PatchUserTest extends UserControllerTest {
    @Test
    @DisplayName("When Patch User with new data, must return new username, then status 200")
    void patchUserAndNewData() throws Exception {
        String newUsername = "New username";
        PatchUserDto patchUserDto = new PatchUserDto(
                "user@mail.tld",
                newUsername,
                "Password123",
                "NewPassword123"
        );
        UserDto userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByUsername(newUsername)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(newUsername));
    }

    @Test
    @DisplayName("When Patch User with duplicate data, must return status 409")
    void patchDuplicateUsername() throws Exception {
        String newUsername = "New username";
        PatchUserDto patchUserDto = new PatchUserDto(
                "user@mail.tld",
                newUsername,
                "Password123",
                "NewPassword123"
        );
        UserDto userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByUsername(newUsername)).willReturn(true);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("When Patch nonexistent User, must return status 404")
    void patchNonexistentUser() throws Exception {
        String newUsername = "New username";
        PatchUserDto patchUserDto = new PatchUserDto(
                "user@mail.tld",
                newUsername,
                "Password123",
                "NewPassword123"
        );
        UserDto userDto = new UserDto(newUsername);

        BDDMockito.given(userService.existsUserByUsername(newUsername)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
