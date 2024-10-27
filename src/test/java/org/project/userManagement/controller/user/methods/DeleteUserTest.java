package org.project.userManagement.controller.user.methods;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.project.userManagement.controller.user.UserControllerTest;
import org.project.userManagement.dto.DeleteUserDto;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.ResultActions;
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

        BDDMockito.given(userService.deleteUser(deleteUserDto)).willReturn(true);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @DisplayName("When Delete nonexistent User, must return status 404")
    @WithMockUser
    void deleteNonexistentUser() throws Exception {

        BDDMockito.given(userService.deleteUser(deleteUserDto)).willReturn(false);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteUserDto)));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("When Delete User by unauthenticated User, must return status 403")
    void deleteByUnauthenticatedUser() throws Exception {

        BDDMockito.given(userService.deleteUser(deleteUserDto)).willReturn(true);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deleteUserDto)));

        BDDMockito.verify(userService, Mockito.never()).deleteUser(deleteUserDto);
        response.andExpect(MockMvcResultMatchers.status().isForbidden());
    }
}
