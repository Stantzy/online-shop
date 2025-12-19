package io.github.onlineshop.users.api;

public record UserSearchFilter(
            Long userId,
            Integer pageSize,
            Integer pageNumber
) {

}
