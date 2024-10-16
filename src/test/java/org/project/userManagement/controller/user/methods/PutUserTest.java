package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.PutUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class PutUserTest extends UserControllerTest {
    private PutUserDto putUserDto;

    @BeforeEach
    protected void setPutUserDto() {
        putUserDto = new PutUserDto(
                "user@mail.tld",
                "New username",
                "Password123",
                "NewPassword123"
        );
    }

    @Test
    @DisplayName("When Put existent User with new data, must return new username, then status 200")
    void putUser() throws Exception {
        String username = putUserDto.username();
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("When Put existent User with existent username, must return status 409")
    void putDuplicateUsername() throws Exception {
        String username = putUserDto.username();
        UserDto userDto = new UserDto(username);

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(true);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.of(userDto));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("When Put nonexistent User, must return status 404")
    void putNonexistentUser() throws Exception {
        String username = putUserDto.username();

        BDDMockito.given(userService.existsUserByUsername(username)).willReturn(false);
        BDDMockito.given(userService.putUser(putUserDto)).willReturn(Optional.empty());

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(putUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
