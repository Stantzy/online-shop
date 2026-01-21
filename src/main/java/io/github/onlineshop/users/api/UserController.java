package io.github.onlineshop.users.api;

import io.github.onlineshop.constants.PathConstants;
import io.github.onlineshop.security.domain.CurrentUserService;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.request.UserModifyRequest;
import io.github.onlineshop.users.api.dto.request.UserPaginationRequest;
import io.github.onlineshop.users.api.dto.request.UserPasswordChangeRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.domain.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final CurrentUserService currentUserService;

    public UserController(
        UserService userService,
        CurrentUserService currentUserService
    ) {
        this.userService = userService;
        this.currentUserService = currentUserService;
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
        @PathVariable @NotNull Long id
    ) {
        log.info("Called method getUserById");;
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfileInfo() {
        log.info("Called method getProfileInfo");

        Long userId = currentUserService.getUserId();
        UserDto currentUser = userService.getUserById(userId);

        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/me")
    public ResponseEntity<UserModifyResponse> updateCurrentUserInfo(
        @RequestBody @Valid UserModifyRequest request
    ) {
        log.info(
            "Called updateCurrentUserInfo: {}, {}",
            request.username(),
            request.email()
        );

        Long userId = currentUserService.getUserId();
        UserModifyResponse updatedUser =
            userService.updateUser(userId, request);

        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping
    public ResponseEntity<UserCreateResponse> createUser(
        @RequestBody @Valid UserCreateRequest userToCreate
    ) {
        log.info("Called method createUser");
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(userService.createUser(userToCreate));
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<UserModifyResponse> updateUser(
        @PathVariable @NotNull Long id,
        @RequestBody @Valid UserModifyRequest userToModify
    ) {
        log.info("Called method updateUser");
        return ResponseEntity.ok(userService.updateUser(id, userToModify));
    }

    @PutMapping("/{id}/change_password")
    public ResponseEntity<Boolean> changePassword(
        @PathVariable @NotNull Long id,
        @RequestBody @Valid UserPasswordChangeRequest passwordChangeRequest
    ) {
        log.info("Called method changePassword: id={}", id);
        return ResponseEntity.ok(
            userService.changePassword(id, passwordChangeRequest)
        );
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteUserById(
        @PathVariable @NotNull Long id
    ) {
        log.info("Called method deleteUserById: id={}", id);
        userService.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}
