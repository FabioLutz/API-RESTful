package org.project.userManagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
        @Email(message = "Invalid email")
        @Size(min = 5, max = 50, message = "The email must be between 5 and 50 characters")
        @NotBlank(message = "The email must not be blank")
        @Column(name = "email", unique = true, nullable = false, length = 50, updatable = false)
        String email,

        @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters")
        @NotBlank(message = "The username must not be blank")
        @Column(name = "username", unique = true, nullable = false, length = 100)
        String username,

        @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
        @NotBlank(message = "The password must not be blank")
        @Column(name = "password", nullable = false, length = 128)
        String password
) {
}