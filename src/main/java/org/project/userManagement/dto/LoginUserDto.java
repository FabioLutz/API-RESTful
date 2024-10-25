package org.project.userManagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginUserDto(
        @Email(message = "Invalid email")
        @NotBlank(message = "The email must not be blank")
        @Column(name = "email", unique = true, nullable = false, length = 50, updatable = false)
        String email,

        @NotBlank(message = "The password must not be blank")
        @Column(name = "password", nullable = false, length = 128)
        String password
) {
}
