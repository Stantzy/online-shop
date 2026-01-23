package io.github.onlineshop.users.api.dto.response;

import io.github.onlineshop.security.UserRole;
import lombok.Value;

import java.time.LocalDate;

@Value
public class UserCreateResponse {
    Long id;
    String username;
    String email;
    LocalDate registrationDate;
    UserRole role;
}
