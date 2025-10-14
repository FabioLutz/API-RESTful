package org.project.userManagement.service.user.methods;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.model.CustomUserDetails;
import org.project.userManagement.model.User;
import org.project.userManagement.service.user.UserServiceTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class GetOwnProfileTest extends UserServiceTest {
    @Mock
    private Authentication authentication;

    @Mock
    private CustomUserDetails customUserDetails;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    protected void setUp() {
        Mockito.when(authentication.getPrincipal()).thenReturn(customUserDetails);
        Mockito.when(customUserDetails.getId()).thenReturn(id);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("When getOwnProfile has existent user, must return user profile")
    void getExistentUser() {
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
        Mockito.when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto dtoResult = Assertions.assertDoesNotThrow(() -> userService.getOwnProfile());

        Assertions.assertNotNull(dtoResult);
        Assertions.assertEquals(userDto, dtoResult);
        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userMapper).userToUserDto(user);
    }

    @Test
    @DisplayName("When getOwnProfile has nonexistent user, must throw UserNotFoundException")
    void getNonexistentUser() {
        Mockito.when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException userNotFoundException = Assertions.assertThrows(
                UserNotFoundException.class,
                () -> userService.getOwnProfile()
        );

        Assertions.assertEquals("User not found", userNotFoundException.getMessage());
        Mockito.verify(userRepository).findById(id);
        Mockito.verify(userMapper, Mockito.never()).userToUserDto(Mockito.any(User.class));
    }
}
