package io.github.onlineshop.users.api;

import io.github.onlineshop.users.api.dto.UserCreateRequest;
import io.github.onlineshop.users.api.dto.UserCreateResponse;
import io.github.onlineshop.users.api.dto.UserDto;
import io.github.onlineshop.users.domain.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger log =
            LoggerFactory.getLogger(UserController.class);
    private final UserService userService;

    public UserController(
        UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        log.info("Called method getAllUsers");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable Long id
    ) {
        log.info("Called method getUserById");;
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(
            @RequestBody UserCreateRequest userToCreate
    ) {
        log.info("Called method createUser");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(userToCreate));
    }
}
