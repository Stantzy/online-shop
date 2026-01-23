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
    public UserDto toUserDto(User user) {
        return new UserDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRegistrationDate(),
            user.getRole()
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
            userDto.getId(),
            userDto.getUsername(),
            userDto.getEmail(),
            null,
            userDto.getRegistrationDate(),
            null,
            userDto.getRole()
        );
    }

    public User toDomainUser(UserCreateRequest userToCreate) {
        return new User(
            null,
            userToCreate.getUsername(),
            userToCreate.getEmail(),
            userToCreate.getPassword(),
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

        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setEmail(user.getEmail());
        userEntity.setPasswordHash(user.getPasswordHash());
        userEntity.setRegistrationDate(user.getRegistrationDate());
        userEntity.setRole(user.getRole());

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
