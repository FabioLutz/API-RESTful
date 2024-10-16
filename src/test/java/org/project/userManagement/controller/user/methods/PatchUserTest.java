package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
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
    private PatchUserDto patchUserDto;

    @BeforeEach
    protected void setPatchUserDto() {
        patchUserDto = new PatchUserDto(
                "user@mail.tld",
                "New username",
                "Password123",
                "NewPassword123"
        );
    }

    @Test
    @DisplayName("When Patch User with new data, must return new username, then status 200")
    void patchUserAndNewData() throws Exception {
        String username = patchUserDto.username();
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Patch User with duplicate data, must return status 409")
    void patchDuplicateUsername() throws Exception {
        String username = patchUserDto.username();
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(true);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("When Patch nonexistent User, must return status 404")
    void patchNonexistentUser() throws Exception {
        String username = patchUserDto.username();

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.patchUser(patchUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.patch("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
