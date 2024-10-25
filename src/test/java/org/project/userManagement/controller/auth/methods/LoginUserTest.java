package org.project.userManagement.controller.auth.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.auth.AuthControllerTest;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class LoginUserTest extends AuthControllerTest {
    private LoginUserDto loginUserDto;

    private UserDto userDto;

    @BeforeEach
    protected void setLoginUserDto() {
        loginUserDto = new LoginUserDto(
                "user@mail.tld",
                "Password123"
        );

        userDto = new UserDto("User");
    }

    @Test
    @DisplayName("When login valid user, must return status 200")
    void loginValidUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByEmail(loginUserDto.email())).willReturn(Optional.of(userDto));
        BDDMockito.given(authService.verify(loginUserDto)).willReturn(true);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User"));
    }

    @Test
    @DisplayName("When login invalid password, must return status 401")
    void loginInvalidUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByEmail(loginUserDto.email())).willReturn(Optional.of(userDto));
        BDDMockito.given(authService.verify(loginUserDto)).willReturn(false);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("When login nonexistent user, must return status 404")
    void loginNonexistentUser() throws Exception {

        BDDMockito.given(userService.findUserDtoByEmail(loginUserDto.email())).willReturn(Optional.empty());
        BDDMockito.given(authService.verify(loginUserDto)).willReturn(false);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
