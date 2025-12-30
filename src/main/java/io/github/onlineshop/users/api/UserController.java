package io.github.onlineshop.users.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.request.UserModifyRequest;
import io.github.onlineshop.users.api.dto.request.UserPaginationRequest;
import io.github.onlineshop.users.api.dto.request.UserPasswordChangeRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.domain.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathConstants.USER)
@Validated
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
    public ResponseEntity<List<UserDto>> getAllUsers(
        @Valid UserPaginationRequest pagination
    ) {
        log.info(
            "Called method getAllUsers: sortBy={}, sortDirection={} " +
            "pageNumber={}, pageSize={}",
            pagination.sortBy(),
            pagination.sortDirection(),
            pagination.pageNumber(),
            pagination.pageSize()
        );

        return ResponseEntity.ok(
            userService.getAllUsers(pagination)
        );
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

    @PutMapping("/{id}/update_user")
    public ResponseEntity<UserModifyResponse> updateUser(
        @PathVariable Long id,
        @RequestBody UserModifyRequest userToModify
    ) {
        log.info("Called method updateUser");
        return ResponseEntity.ok(userService.updateUser(id, userToModify));
    }

    @PutMapping("/{id}/change_password")
    public ResponseEntity<Boolean> changePassword(
        @PathVariable Long id,
        @RequestBody UserPasswordChangeRequest passwordChangeRequest
    ) {
        log.info("Called method changePassword: id={}", id);
        return ResponseEntity.ok(
            userService.changePassword(id, passwordChangeRequest)
        );
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUserById(
        @PathVariable Long id
    ) {
        log.info("Called method deleteUserById: id={}", id);
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
