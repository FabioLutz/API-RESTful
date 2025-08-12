package org.project.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DeleteUserDto(
        @Email(message = "Invalid email")
        @NotBlank(message = "The email must not be blank")
        String email,

        @NotBlank(message = "The password must not be blank")
        String password
) {
}
