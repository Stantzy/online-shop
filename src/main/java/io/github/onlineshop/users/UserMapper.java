package io.github.onlineshop.users;

import io.github.onlineshop.security.UserRole;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    public UserMapper() {}

    public UserDto toUserDto(User user) {
        return new UserDto(
            user.id(),
            user.username(),
            user.email(),
            user.registrationDate(),
            user.role()
        );
    }

    public UserDto toUserDto(UserEntity userEntity) {
        return new UserDto(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            userEntity.getRegistrationDate(),
            userEntity.getRole()
        );
    }

    public User toDomainUser(UserDto userDto) {
        return new User(
            userDto.id(),
            userDto.username(),
            userDto.email(),
            null,
            userDto.registrationDate(),
            null,
            userDto.role()
        );
    }

    public User toDomainUser(UserCreateRequest userToCreate) {
        return new User(
            null,
            userToCreate.username(),
            userToCreate.email(),
            userToCreate.password(),
            null,
            null,
            UserRole.USER
        );
    }

    public User toDomainUser(UserEntity userEntity) {
        return new User(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            userEntity.getPasswordHash(),
            userEntity.getRegistrationDate(),
            null,
            userEntity.getRole()
        );
    }

    public UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setId(user.id());
        userEntity.setUsername(user.username());
        userEntity.setEmail(user.email());
        userEntity.setPasswordHash(user.passwordHash());
        userEntity.setRegistrationDate(user.registrationDate());
        userEntity.setRole(user.role());

        return userEntity;
    }

    public UserCreateResponse toCreateResponse(UserEntity userEntity) {
        return new UserCreateResponse(
            userEntity.getId(),
            userEntity.getUsername(),
            userEntity.getEmail(),
            userEntity.getRegistrationDate(),
            userEntity.getRole()
        );
    }

    public UserModifyResponse toModifyResponse(UserEntity userEntity) {
        return new UserModifyResponse(
            userEntity.getUsername(),
            userEntity.getEmail()
        );
    }
}
