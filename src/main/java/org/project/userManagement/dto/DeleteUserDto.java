package org.project.userManagement.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserDto {
    @Email(message = "Invalid email")
    @NotBlank(message = "The email must not be blank")
    @Column(name = "email", unique = true, nullable = false, length = 50, updatable = false)
    private String email;

    @NotBlank(message = "The password must not be blank")
    @Column(name = "password", nullable = false, length = 128)
    private String password;
}
