package org.project.userManagement.controller.auth.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.auth.AuthControllerTest;
import org.project.userManagement.dto.LoginUserDto;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class LoginUserTest extends AuthControllerTest {

    private final String token = "token";

    private LoginUserDto loginUserDto;

    @BeforeEach
    protected void setLoginUserDto() {
        loginUserDto = new LoginUserDto(
                "user@mail.tld",
                "Password123"
        );
    }

    @Test
    @DisplayName("When login valid user, must return status 200")
    void loginValidUser() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(loginUserDto.email())).willReturn(true);
        BDDMockito.given(authService.loginUser(loginUserDto)).willReturn(token);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
    }

    @Test
    @DisplayName("When login invalid password, must return status 401")
    void loginInvalidUser() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(loginUserDto.email())).willReturn(true);
        BDDMockito.given(authService.loginUser(loginUserDto)).willThrow(new BadCredentialsException(""));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("When login nonexistent user, must return status 404")
    void loginNonexistentUser() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(loginUserDto.email())).willReturn(false);
        BDDMockito.given(authService.loginUser(loginUserDto)).willReturn(token);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginUserDto)));

        BDDMockito.verify(authService, Mockito.never()).loginUser(loginUserDto);
        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
