package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.CreateUserDto;
import org.project.userManagement.dto.UserDto;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class PostUserTest extends UserControllerTest {
    @Test
    @DisplayName("when Post valid User, must return username, then status 201")
    void postValidUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "user@mail.tld",
                "User",
                "Password123"
        );

        BDDMockito.given(userService.existsUserByEmail(createUserDto.email())).willReturn(false);
        BDDMockito.given(userService.existsUserByUsername(createUserDto.username())).willReturn(false);
        BDDMockito.given(userService.createUser(createUserDto)).willReturn(new UserDto(createUserDto.username()));

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("User"));
    }
}
