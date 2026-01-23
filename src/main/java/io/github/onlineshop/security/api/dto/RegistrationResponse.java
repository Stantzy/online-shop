package io.github.onlineshop.security.api.dto;

import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import lombok.Value;

@Value
public class RegistrationResponse {
    UserCreateResponse user;
    String jwtToken;
}
