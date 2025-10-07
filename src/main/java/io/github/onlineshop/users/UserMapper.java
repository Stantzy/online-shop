package io.github.onlineshop.users;

import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.orders.database.OrderEntity;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {
    private final OrderMapper orderMapper;

    public UserMapper(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public UserDto toUserDto(User user) {
        return new UserDto(
                user.username(),
                user.email(),
                user.registrationDate(),
                user.orders()
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
                userDto.registrationDate(),
                userDto.orders()
        );
    }

    public User toDomainUser(UserCreateRequest userToCreate) {
        return new User(
                userToCreate.username(),
                userToCreate.email(),
                userToCreate.password(),
                null,
                null
        );
    }

    public User toDomainUser(UserEntity userEntity) {
        List<OrderDto> orderDtoList = userEntity.getOrders()
            .stream()
            .map(orderMapper::toOrderDto)
            .toList();

        return new User(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getPasswordHash(),
                userEntity.getRegistrationDate(),
                orderDtoList
        );
    }

    public UserEntity toUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        if(user.orders() != null) {
            List<OrderEntity> orderEntityList = user.orders()
                    .stream()
                    .map(orderMapper::toOrderEntity)
                    .toList();

            userEntity.setOrders(orderEntityList);
        }

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
                userEntity.getEmail(),
                userEntity.getRegistrationDate()
        );
    }

    public UserModifyResponse toModifyResponse(UserEntity userEntity) {
        return new UserModifyResponse(
                userEntity.getUsername(),
                userEntity.getEmail()
        );
    }
}
