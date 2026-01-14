package io.github.onlineshop.security.api.dto;

import io.github.onlineshop.users.api.dto.response.UserCreateResponse;

public record RegistrationResponse(
    UserCreateResponse user,
    String jwtToken
) {

}
