package org.project.userManagement.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "users_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Size(min = 2, max = 50, message = "The username must be between 2 and 50 characters")
    @NotBlank(message = "The username must not be blank")
    @Column(name = "username", unique = true, nullable = false, length = 100)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, max = 128, message = "The password must be between 8 and 128 characters")
    @NotBlank(message = "The password must not be blank")
    @Column(name = "password", unique = true, nullable = false, length = 128)
    private String password;
}
