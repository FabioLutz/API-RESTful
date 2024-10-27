package org.project.userManagement.controller.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.userManagement.controller.AuthController;
import org.project.userManagement.repositories.UserRepository;
import org.project.userManagement.security.SecurityConfig;
import org.project.userManagement.security.SecurityFilter;
import org.project.userManagement.security.TokenService;
import org.project.userManagement.service.AuthService;
import org.project.userManagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@Import({SecurityConfig.class, SecurityFilter.class, TokenService.class})
@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected UserService userService;

    @MockBean
    protected AuthService authService;

    @MockBean
    protected UserRepository userRepository;
}