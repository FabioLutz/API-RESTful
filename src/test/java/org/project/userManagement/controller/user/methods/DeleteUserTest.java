package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.DeleteUserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class DeleteUserTest extends UserControllerTest {
    private DeleteUserDto deleteUserDto;

    @BeforeEach
    protected void setDeleteUserDto() {
        deleteUserDto = new DeleteUserDto(
                "user@mail.tld",
                "Password123"
        );
    }

    @Test
    @DisplayName("When Delete User, must return status 204")
    @WithMockUser
    void deleteUser() throws Exception {
        Mockito.doNothing().when(userService).deleteUser(deleteUserDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("When Delete nonexistent User, must return status 404")
    @WithMockUser
    void deleteNonexistentUser() throws Exception {
        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(userService).deleteUser(deleteUserDto);

        mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deleteUserDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
