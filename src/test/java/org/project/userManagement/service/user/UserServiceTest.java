package org.project.userManagement.service.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.project.userManagement.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public abstract class UserServiceTest {
    @Mock
    protected UserMapper userMapper;

    @Mock
    protected UserRepository userRepository;

    @Mock
    protected PasswordEncoder passwordEncoder;

    @InjectMocks
    protected UserService userService;

    protected final Long id = 42L;
    protected final String email = "user@mail.tld";
    protected final String username = "User";
    protected final String password = "Password123";
    protected final String newPassword = "NewPassword123";
    protected final String encryptedPassword = "EncryptedPassword";

    protected User user;
    protected UserDto userDto;

    @BeforeEach
    protected void setUser() {
        user = new User(
                id,
                email,
                username,
                password,
                UserRole.USER
        );
    }

    @BeforeEach
    protected void setUserDto(){
        userDto = new UserDto(username);
    }
}
