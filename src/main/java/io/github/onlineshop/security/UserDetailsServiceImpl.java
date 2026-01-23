package io.github.onlineshop.security;

import io.github.onlineshop.users.database.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserPrincipal loadUserByUsername(String username) {
        return userRepository.findByUsername(username)
            .map(userEntity -> new UserPrincipal(
                    userEntity.getId(),
                    userEntity.getUsername(),
                    userEntity.getEmail(),
                    userEntity.getPasswordHash(),
                    userEntity.getRole()
                )
            )
            .orElseThrow(
                () -> new UsernameNotFoundException(
                    "Failed to retrieve user: " + username
                )
            );
    }
}
