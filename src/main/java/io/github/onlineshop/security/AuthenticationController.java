package io.github.onlineshop.security;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.dto.JwtRequest;
import io.github.onlineshop.security.dto.JwtResponse;
import io.github.onlineshop.security.jwt.JwtTokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.AUTH)
public class AuthenticationController {
    private static final Logger log =
            LoggerFactory.getLogger(AuthenticationController.class);

    private final UserDetailsService userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(
            UserDetailsService userService,
            JwtTokenUtils jwtTokenUtils,
            AuthenticationManager authenticationManager
    ) {
        this.userService = userService;
        this.jwtTokenUtils = jwtTokenUtils;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(
            @RequestBody JwtRequest authRequest
    ) {
        log.info("Called method createAuthToken");
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
                (UserPrincipal) userService.loadUserByUsername(authRequest.username());
        String token = jwtTokenUtils.generateToken(user);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
