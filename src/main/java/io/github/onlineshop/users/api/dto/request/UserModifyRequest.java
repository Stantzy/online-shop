package io.github.onlineshop.users.api.dto.request;

import jakarta.validation.constraints.Email;

public record UserModifyRequest(
    String username,

    @Email(message = "Wrong email address format")
    String email
) {

}
