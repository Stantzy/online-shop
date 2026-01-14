package io.github.onlineshop.security.domain;

import io.github.onlineshop.security.UserPrincipal;
import io.github.onlineshop.security.api.dto.JwtRequest;
import io.github.onlineshop.security.api.dto.JwtResponse;
import io.github.onlineshop.security.jwt.JwtTokenUtils;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.security.api.dto.RegistrationResponse;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {
    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationService.class);

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final JwtTokenUtils jwtTokenUtils;

    public AuthenticationService(
        AuthenticationManager authenticationManager,
        UserDetailsService userDetailsService,
        UserService userService,
        JwtTokenUtils jwtTokenUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public JwtResponse generateToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authRequest.username(), authRequest.password()
                )
            );
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }

        UserPrincipal user =
            (UserPrincipal) userDetailsService.loadUserByUsername(authRequest.username());
        String token = jwtTokenUtils.generateToken(user);

        return new JwtResponse(token);
    }

    public RegistrationResponse register(UserCreateRequest request) {

        UserCreateResponse newUser = userService.createUser(request);
        JwtResponse jwtToken = null;

        if(newUser != null) {
            jwtToken = generateToken(
                new JwtRequest(request.username(), request.password())
            );
        }

        return new RegistrationResponse(
            newUser,
            Objects.requireNonNull(jwtToken).jwtToken()
        );
    }
}
