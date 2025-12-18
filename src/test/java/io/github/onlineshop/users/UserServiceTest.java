package io.github.onlineshop.users;

import io.github.onlineshop.orders.OrderMapper;
import io.github.onlineshop.orders.api.dto.OrderDto;
import io.github.onlineshop.security.UserRole;
import io.github.onlineshop.users.api.dto.UserDto;
import io.github.onlineshop.users.database.UserEntity;
import io.github.onlineshop.users.database.UserRepository;
import io.github.onlineshop.users.domain.UserService;
import io.github.onlineshop.util.TestConstants;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userEntity = new UserEntity();
        userEntity.setId(TestConstants.USER_ID);
        userEntity.setUsername(TestConstants.USER_NAME);
        userEntity.setEmail(TestConstants.USER_EMAIL);
        userEntity.setPasswordHash(TestConstants.USER_HASH);
        userEntity.setRegistrationDate(LocalDate.now());
        userEntity.setOrders(Collections.emptyList());
    }

    @Test
    void gerUserById_shouldReturnUserDto_whenUserExists() {
        // given
        when(userRepository.findById(TestConstants.USER_ID))
                .thenReturn(Optional.of(userEntity));

        UserDto mappedUserDto = new UserDto(
                userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getRegistrationDate(),
                Collections.emptyList(),
                UserRole.USER
        );
        when(userMapper.toUserDto(userEntity)).thenReturn(mappedUserDto);

        // when
        UserDto result = userService.getUserById(TestConstants.USER_ID);

        // then
        assertNotNull(result);
        assertEquals(TestConstants.USER_NAME, result.username());
        assertEquals(TestConstants.USER_EMAIL, result.email());
        assertEquals(
                userEntity.getRegistrationDate(),
                result.registrationDate()
        );
        assertTrue(result.orders().isEmpty());

        verify(userRepository).findById(TestConstants.USER_ID);
        verify(userMapper).toUserDto(userEntity);
        verifyNoMoreInteractions(userRepository, userMapper);
    }

    @Test
    void getUserById_shouldThrowException_whenUserNotFound() {
        // given
        when(userRepository.findById(TestConstants.WRONG_USER_ID))
                .thenReturn(Optional.empty());

        // when / then
        assertThrows(
                EntityNotFoundException.class,
                () -> userService.getUserById(TestConstants.WRONG_USER_ID)
        );

        verify(userRepository).findById(TestConstants.WRONG_USER_ID);
        verifyNoInteractions(userMapper);
    }
}
