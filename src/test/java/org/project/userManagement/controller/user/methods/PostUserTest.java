package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.CreateUserDto;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostUserTest extends UserControllerTest {
    @Test
    @DisplayName("when Post valid User, must return username, then status 201")
    void postValidUser() throws Exception {
        CreateUserDto createUserDto = new CreateUserDto(
                "user@mail.tld",
                "User",
                "Password123"
        );

        mockMvc.perform(post("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("User"));
    }
}
