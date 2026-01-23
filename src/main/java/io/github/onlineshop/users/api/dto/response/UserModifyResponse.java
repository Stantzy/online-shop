package io.github.onlineshop.users.api.dto.response;

import lombok.Value;

@Value
public class UserModifyResponse {
    String username;
    String email;
}
