package io.github.onlineshop.users.domain;

import io.github.onlineshop.security.UserRole;
import io.github.onlineshop.users.UserMapper;
import io.github.onlineshop.users.api.dto.request.UserCreateRequest;
import io.github.onlineshop.users.api.dto.request.UserModifyRequest;
import io.github.onlineshop.users.api.dto.request.UserPaginationRequest;
import io.github.onlineshop.users.api.dto.request.UserPasswordChangeRequest;
import io.github.onlineshop.users.api.dto.response.UserCreateResponse;
import io.github.onlineshop.users.api.dto.response.UserDto;
import io.github.onlineshop.users.api.dto.response.UserModifyResponse;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger log =
        LoggerFactory.getLogger(UserService.class);

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    public List<UserDto> getAllUsers(
        UserPaginationRequest pagination
    ) {
        log.info("Called getAllUsers");

        List<UserEntity> userEntities =
            repository.findAll(pagination.toPageable()).toList();

        return userEntities.stream()
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

    // FIXME What if user already exists?
    // FIXME What if username or email is exists?
    public UserCreateResponse createUser(
                UserCreateRequest userToCreate
    ) {
        log.info("Called method createUser");

        User userToSave = new User(
            null,
            userToCreate.getUsername(),
            userToCreate.getEmail(),
            encoder.encode(userToCreate.getPassword()),
            LocalDate.now(),
            null,
            UserRole.USER
        );

        UserEntity entityToSave = mapper.toUserEntity(userToSave);
        UserEntity savedEntity = repository.save(entityToSave);

        return mapper.toCreateResponse(savedEntity);
    }

    public UserModifyResponse updateUser(
        Long id,
        UserModifyRequest userToModify
    ) {
        log.info(
            "Called method updateUser: userId={}, newUsername={}, newEmail={}",
            id,
            userToModify.getUsername(),
            userToModify.getEmail()
        );

        UserEntity entityToUpdate =
            repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    "Not found user by id " + id)
                );

        validateUnique("username", userToModify.getUsername());
        validateUnique("email", userToModify.getEmail());

        if(!strIsNullOrIsBlank(userToModify.getUsername()))
            entityToUpdate.setUsername(userToModify.getUsername());

        if(!strIsNullOrIsBlank(userToModify.getEmail()))
            entityToUpdate.setEmail(userToModify.getEmail());

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
            passwordChangeRequest.getOldPassword(), userEntity.getPasswordHash()
        );
        if(!isMatch) {
            throw new IllegalArgumentException("Old password is wrong");
        }
        userEntity.setPasswordHash(
            encoder.encode(passwordChangeRequest.getNewPassword())
        );
        repository.save(userEntity);

        return true;
    }

    public void deleteUserById(Long id) {
        log.info("Called method deleteUserById: id={}", id);
        repository.deleteById(id);
    }

    private void validateUnique(String fieldName, String value) {
        if(strIsNullOrIsBlank(fieldName) || strIsNullOrIsBlank(value))
            return;

        boolean alreadyExists = switch(fieldName.toLowerCase()) {
            case "username" -> repository.findByUsername(value).isPresent();
            case "email" -> repository.findByEmail(value).isPresent();
            default -> throw new
                IllegalArgumentException("Unexpected value: " + fieldName);
        };

        if(alreadyExists) {
            throw new IllegalArgumentException(
                fieldName + " is already in use: " + value
            );
        }
    }

    private boolean strIsNullOrIsBlank(String str) {
        return str == null || str.isBlank();
    }
}
