package io.github.onlineshop.users.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserCreateRequest(
    @NotBlank(message = "Username must not be blank")
    String username,

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Wrong email address format")
    String email,

    @NotBlank(message = "Password must not be blank")
    String password
) {

}
