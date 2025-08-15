package org.project.userManagement.controller.auth.methods;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.auth.AuthControllerTest;
import org.project.userManagement.dto.LoginUserDto;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class LoginUserTest extends AuthControllerTest {

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
        String token = "token";
        BDDMockito.given(authService.loginUser(loginUserDto)).willReturn(token);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").value("token"));
    }

    @Test
    @DisplayName("When login invalid password, must return status 401")
    void loginInvalidUser() throws Exception {
        BDDMockito.given(authService.loginUser(loginUserDto)).willThrow(new BadCredentialsException(""));

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("When login nonexistent user, must return status 404")
    void loginNonexistentUser() throws Exception {
        BDDMockito.given(authService.loginUser(loginUserDto)).willThrow(new EntityNotFoundException());

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
