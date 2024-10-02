package org.project.userManagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PutUserDto {
    @Email(message = "Invalid email")
    @Size(min = 5, max = 50, message = "The email must be between 5 and 50 characters")
    @NotBlank(message = "The email must not be blank")
    @Column(name = "email", unique = true, nullable = false, length = 50, updatable = false)
    private String email;

    @Size(min = 2, max = 100, message = "The username must be between 2 and 100 characters")
    @NotBlank(message = "The username must not be blank")
    @Column(name = "username", unique = true, length = 100)
    private String username;

    @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
    @NotBlank(message = "The password must not be blank")
    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
    @NotBlank(message = "The new password must not be blank")
    @Column(name = "password", length = 128)
    private String newPassword;
}