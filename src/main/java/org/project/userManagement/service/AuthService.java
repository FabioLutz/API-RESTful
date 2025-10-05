package org.project.userManagement.service;

import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.exception.InvalidCredentialsException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.repositories.UserRepository;
import org.project.userManagement.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserMapper userMapper;

    public String loginUser(LoginUserDto loginUserDto) {
        try {
            AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            UserDetails principal = (UserDetails) authenticate.getPrincipal();

            return tokenService.generateToken(principal);
        } catch (UserNotFoundException | AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
