package org.project.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @Email(message = "Invalid email")
        @Size(min = 5, max = 50, message = "The email must be between 5 and 50 characters")
        @NotBlank(message = "The email must not be blank")
        String email,

        @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters")
        @NotBlank(message = "The username must not be blank")
        String username,

        @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
        @NotBlank(message = "The password must not be blank")
        String password
) {
}
