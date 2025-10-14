package org.project.userManagement.initializer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.userManagement.exception.InitialAdminPasswordNotDefinedException;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
@Slf4j
public class AdminUserInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${api.security.initialAdmin.password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        String ADMIN_USER_USERNAME = "admin";

        if (userRepository.findByUsername(ADMIN_USER_USERNAME).isEmpty()) {
            User admin = new User();

            admin.setEmail("admin@admin.adm");
            admin.setUsername(ADMIN_USER_USERNAME);
            admin.setPassword(passwordEncoder.encode(getAdminPassword()));
            admin.setRole(UserRole.ADMIN);

            userRepository.save(admin);
            log.info("Admin user created");
        }
    }

    private String getAdminPassword() {
        if (password == null || password.isBlank()) {
            throw new InitialAdminPasswordNotDefinedException("Initial admin password is invalid or missing");
        }
        return password;
    }
}
