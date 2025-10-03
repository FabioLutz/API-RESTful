package org.project.userManagement.service;

import org.project.userManagement.dto.LoginUserDto;
import org.project.userManagement.dto.RegisterUserDto;
import org.project.userManagement.dto.UserDto;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.mapper.UserMapper;
import org.project.userManagement.model.CustomUserDetails;
import org.project.userManagement.model.User;
import org.project.userManagement.model.UserRole;
import org.project.userManagement.repositories.UserRepository;
import org.project.userManagement.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public String loginUser(LoginUserDto loginUserDto) {
        AuthenticationManager authenticationManager = applicationContext.getBean(AuthenticationManager.class);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        UserDetails principal = (UserDetails) authenticate.getPrincipal();

        return tokenService.generateToken(principal);
    }

    public UserDto registerUser(RegisterUserDto registerUserDto) {
        User newUser = userMapper.registerUserDtoToUser(registerUserDto);
        String encryptedPassword = passwordEncoder.encode(registerUserDto.password());
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.USER);
        User user = userRepository.save(newUser);
        return userMapper.userToUserDto(user);
    }
}
