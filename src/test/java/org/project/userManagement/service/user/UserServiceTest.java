package org.project.userManagement.service.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.project.userManagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class UserServiceTest {

    @Mock
    protected UserRepository userRepository;

    @Spy
    protected PasswordEncoder passwordEncoder;

    @InjectMocks
    protected UserService userService;

    protected AutoCloseable autoCloseable;

    @Spy
    protected UserMapper userMapper = UserMapper.INSTANCE;

    @BeforeEach
    protected void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    protected void tearDown() throws Exception {
        autoCloseable.close();
    }

    protected final String email = "user@mail.tld";
    protected final String username = "User";
    protected final String password = "Password123";
    protected final String newUsername = "New Username";
    protected final String newPassword = "NewPassword123";
    protected final String encryptedPassword = "EncryptedPassword";
    protected User user;

    @BeforeEach
    protected void setUser() {
        user = new User(
                1L,
                email,
                username,
                password,
                UserRole.USER
        );
    }
}
