package io.github.onlineshop.users.domain;

import io.github.onlineshop.security.UserRole;
import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.request.UserModifyRequest;
import io.github.onlineshop.users.api.dto.request.UserPasswordChangeRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserService {
    private static final Logger log =
        LoggerFactory.getLogger(UserService.class);
    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(
        UserRepository repository,
        UserMapper mapper,
        PasswordEncoder encoder
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    public List<UserDto> getAllUsers() {
        log.info("Called getAllUsers");

        List<UserEntity> allEntities = repository.findAll();
        List<User> allUsers = allEntities.stream()
            .map(mapper::toDomainUser)
            .toList();

        return allUsers.stream()
            .map(mapper::toUserDto)
            .toList();
    }

    public UserDto getUserById(Long id) {
        log.info("Called method getUserById: id={}", id);

        UserEntity userEntity =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Not found user by id=" + id)
                );

        return mapper.toUserDto(userEntity);
    }

    public UserCreateResponse createUser(
                UserCreateRequest userToCreate
    ) {
        log.info("Called method createUser");

        User userToSave = new User(
            userToCreate.username(),
            userToCreate.email(),
            encoder.encode(userToCreate.password()),
            LocalDate.now(),
            null,
            UserRole.USER
        );

        UserEntity entityToSave = mapper.toUserEntity(userToSave);
        UserEntity savedEntity = repository.save(entityToSave);

        return mapper.toCreateResponse(savedEntity);
    }

    public UserModifyResponse updateUserRole(
        Long id,
        UserModifyRequest userToModify
    ) {
        log.info("Called method updateUserRole");

        UserEntity entityToUpdate =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Not found user by id " + id)
                );

        entityToUpdate.setRole(userToModify.role());

        UserEntity updatedEntity = repository.save(entityToUpdate);

        return mapper.toModifyResponse(updatedEntity);
    }

    public UserModifyResponse updateUser(
        Long id,
        UserModifyRequest userToModify
    ) {
        log.info("Called method updateUser");
        UserEntity entityToUpdate =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Not found user by id " + id)
                );

        entityToUpdate.setUsername(userToModify.username());
        entityToUpdate.setEmail(userToModify.email());

        UserEntity updatedEntity = repository.save(entityToUpdate);

        return mapper.toModifyResponse(updatedEntity);
    }

    public Boolean changePassword(
        Long id,
        UserPasswordChangeRequest passwordChangeRequest
    ) {
        log.info("Called method changePassword: id={}", id);

        UserEntity userEntity =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Not found user by id=" + id)
                );

        boolean isMatch = encoder.matches(
            passwordChangeRequest.oldPassword(), userEntity.getPasswordHash()
        );
        if(!isMatch) {
            throw new IllegalArgumentException("Old password is wrong");
        }
        userEntity.setPasswordHash(
            encoder.encode(passwordChangeRequest.newPassword())
        );
        repository.save(userEntity);

        return true;
    }

    public void deleteUserById(Long id) {
        log.info("Called method deleteUserById: id={}", id);
        repository.deleteById(id);
    }
}
