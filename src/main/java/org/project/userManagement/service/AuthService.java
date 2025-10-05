package org.project.userManagement.service;

import lombok.RequiredArgsConstructor;
import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.exception.InvalidCredentialsException;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.security.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public String loginUser(LoginUserDto loginUserDto) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
            Authentication authenticate = authenticationManager.authenticate(authenticationToken);

            UserDetails principal = (UserDetails) authenticate.getPrincipal();

            return tokenService.generateToken(principal);
        } catch (UserNotFoundException | AuthenticationException e) {
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
