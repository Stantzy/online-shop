package io.github.onlineshop.users.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.api.dto.JwtRequest;
import io.github.onlineshop.security.api.dto.JwtResponse;
import io.github.onlineshop.security.domain.AuthenticationService;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.response.RegistrationResponse;
import io.github.onlineshop.users.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(PathConstants.REGISTRATION)
public class RegistrationController {
    private static final Logger log =
        LoggerFactory.getLogger(RegistrationController.class);

    private final UserService userService;
    private final AuthenticationService authenticationService;

    public RegistrationController(
        UserService userService,
        AuthenticationService authenticationService
    ) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<RegistrationResponse> registerUser(
        @RequestBody UserCreateRequest userToCreate
    ) {
        log.info("Called method registerUser");

        UserCreateResponse newUser = userService.createUser(userToCreate);
        JwtResponse jwtToken = null;

        if(newUser != null) {
            jwtToken = authenticationService.generateToken(
                new JwtRequest(userToCreate.username(), userToCreate.password())
            );
        }

        return ResponseEntity.ok(new RegistrationResponse(newUser, jwtToken));
    }
}
