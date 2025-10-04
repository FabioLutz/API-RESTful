package org.project.userManagement.controller.auth.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.auth.AuthControllerTest;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.RegistrationFailedException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class RegisterUserTest extends AuthControllerTest {
    private RegisterUserDto registerUserDto;

    private final String username = "User";

    @BeforeEach
    protected void setCreateUserDto() {
        registerUserDto = new RegisterUserDto(
                "user@mail.tld",
                username,
                "Password123"
        );
    }

    @Test
    @DisplayName("when Post valid User, must return username, then status 201")
    void postValidUser() throws Exception {
        UserDto userDto = new UserDto(username);

        BDDMockito.given(authService.registerUser(registerUserDto)).willReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(username));
    }

    @Test
    @DisplayName("when Post existing email, must status 409")
    void postExistingEmail() throws Exception {
        BDDMockito.doThrow(new RegistrationFailedException("Registration failed"))
                .when(authService).registerUser(registerUserDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto))).
                andExpect(MockMvcResultMatchers.status().isConflict());
    }

    @Test
    @DisplayName("when Post existing username, must status 409")
    void postExistingUsername() throws Exception {
        BDDMockito.doThrow(new RegistrationFailedException("Registration failed"))
                .when(authService).registerUser(registerUserDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(MockMvcResultMatchers.status().isConflict());
    }
}
