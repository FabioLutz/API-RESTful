package org.project.userManagement.service;

import lombok.RequiredArgsConstructor;
import org.project.userManagement.exception.UserNotFoundException;
import org.project.userManagement.model.CustomUserDetails;
import org.project.userManagement.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository
                .findByEmail(email)
                .map(CustomUserDetails::new)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}
