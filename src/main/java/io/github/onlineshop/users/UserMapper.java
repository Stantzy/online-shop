package io.github.onlineshop.users;

import io.github.onlineshop.users.api.dto.UserCreateRequest;
import io.github.onlineshop.users.api.dto.UserCreateResponse;
import io.github.onlineshop.users.api.dto.UserDto;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return new UserDto(
                user.username(),
                user.email(),
                user.registrationDate()
        );
    }

    public UserDto toUserDto(UserEntity userEntity) {
        User user = toDomainUser(userEntity);
        return toUserDto(user);
    }

    public User toDomainUser(UserDto userDto) {
        return new User(
                userDto.username(),
                userDto.email(),
                null,
                userDto.registrationDate()
        );
    }

    public User toDomainUser(UserCreateRequest userToCreate) {
        return new User(
                userToCreate.username(),
                userToCreate.email(),
                userToCreate.password(),
                null
        );
    }

    public User toDomainUser(UserEntity userEntity) {
        return new User(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                userEntity.getRegistrationDate()
        );
    }

    public UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername(user.username());
        userEntity.setEmail(user.email());
        userEntity.setPasswordHash(user.passwordHash());
        userEntity.setRegistrationDate(user.registrationDate());

        return userEntity;
    }

    public UserEntity toUserEntity(UserDto userDto) {
        User user = toDomainUser(userDto);
        return toUserEntity(user);
    }

    public UserCreateResponse toCreateResponse(UserEntity userEntity) {
        return new UserCreateResponse(
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }
}
