package org.project.userManagement.controller.auth.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.auth.AuthControllerTest;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RegisterUserTest extends AuthControllerTest {
    private RegisterUserDto registerUserDto;

    @BeforeEach
    protected void setCreateUserDto() {
        registerUserDto = new RegisterUserDto(
                "user@mail.tld",
                "User",
                "Password123"
        );
    }

    @Test
    @DisplayName("when Post valid User, must return username, then status 201")
    void postValidUser() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(registerUserDto.email())).willReturn(false);
        BDDMockito.given(userService.existsUserByUsername(registerUserDto.username())).willReturn(false);
        BDDMockito.given(authService.registerUser(registerUserDto)).willReturn(new UserDto(registerUserDto.username()));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User"));
    }

    @Test
    @DisplayName("when Post existing email, must status 409")
    void postExistingEmail() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(registerUserDto.email())).willReturn(true);
        BDDMockito.given(userService.existsUserByUsername(registerUserDto.username())).willReturn(false);
        BDDMockito.given(authService.registerUser(registerUserDto)).willReturn(new UserDto(registerUserDto.username()));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserDto)));

        BDDMockito.verify(userService, Mockito.never()).existsUserByUsername(registerUserDto.username());
        BDDMockito.verify(authService, Mockito.never()).registerUser(registerUserDto);
        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("when Post existing username, must status 409")
    void postExistingUsername() throws Exception {

        BDDMockito.given(userService.existsUserByEmail(registerUserDto.email())).willReturn(false);
        BDDMockito.given(userService.existsUserByUsername(registerUserDto.username())).willReturn(true);
        BDDMockito.given(authService.registerUser(registerUserDto)).willReturn(new UserDto(registerUserDto.username()));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerUserDto)));

        BDDMockito.verify(authService, Mockito.never()).registerUser(registerUserDto);
        response.andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
