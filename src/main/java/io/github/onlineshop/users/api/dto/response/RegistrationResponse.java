package io.github.onlineshop.users.api.dto.response;

import io.github.onlineshop.security.api.dto.JwtResponse;

public record RegistrationResponse(
    UserCreateResponse user,
    JwtResponse token
) {

}
