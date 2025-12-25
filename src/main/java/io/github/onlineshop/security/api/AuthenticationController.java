package io.github.onlineshop.security.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.domain.AuthenticationService;
import io.github.onlineshop.security.api.dto.JwtRequest;
import io.github.onlineshop.security.api.dto.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.AUTH)
public class AuthenticationController {
    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    public AuthenticationController(
        AuthenticationService authenticationService
    ) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> createAuthToken(
        @RequestBody JwtRequest authRequest
    ) {
        log.info("Called method createAuthToken");

        JwtResponse jwtResponse =
            authenticationService.generateToken(authRequest);

        return ResponseEntity.ok(jwtResponse);
    }
}
