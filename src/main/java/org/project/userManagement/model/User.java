package org.project.userManagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users_tb")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email", columnDefinition = "VARCHAR(50)", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "username", columnDefinition = "VARCHAR(100)", unique = true, nullable = false, length = 100)
    private String username;

    @Column(name = "password", columnDefinition = "VARCHAR(128)", nullable = false, length = 128)
    private String password;

    @Column(name = "role", columnDefinition = "SMALLINT", nullable = false)
    private UserRole role;
}
