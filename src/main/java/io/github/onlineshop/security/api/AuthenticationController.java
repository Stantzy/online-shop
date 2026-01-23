package io.github.onlineshop.security.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.domain.AuthenticationService;
import io.github.onlineshop.security.api.dto.JwtRequest;
import io.github.onlineshop.security.api.dto.JwtResponse;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.security.api.dto.RegistrationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(PathConstants.AUTH)
@RequiredArgsConstructor
public class AuthenticationController {
    private static final Logger log =
        LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
        @RequestBody JwtRequest authRequest
    ) {
        log.info("Login attempt for username: {}", authRequest.getUsername());

        JwtResponse jwtResponse =
            authenticationService.generateToken(authRequest);

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(
        @RequestBody UserCreateRequest request
    ) {
        log.info("Registration attempt for username: {}", request.getUsername());
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authenticationService.register(request));
    }
}
