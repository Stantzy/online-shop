package io.github.onlineshop.security;

import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;

public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserPrincipal loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userEntity -> new UserPrincipal(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getEmail(),
                        userEntity.getPasswordHash(),
                        userEntity.getRole()
                ))
                .orElseThrow(
                        () -> new UsernameNotFoundException(
                            "Failed to retrieve user: " + username
                        )
                );
    }
}
