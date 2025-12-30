package io.github.onlineshop.users.api.dto.request;

public record UserSearchFilter(
            Long userId,
            Integer pageSize,
            Integer pageNumber
) {

}
