package org.project.userManagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PatchUserDto(
        @Email(message = "Invalid email")
        @Size(min = 5, max = 50, message = "The email must be between 5 and 50 characters")
        @NotBlank(message = "The email must not be blank")
        String email,

        @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
        @NotBlank(message = "The password must not be blank")
        String password,

        @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
        @NotBlank(message = "The new password must not be blank")
        String newPassword
) {
}
