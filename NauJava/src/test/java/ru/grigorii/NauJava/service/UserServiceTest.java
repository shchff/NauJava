package ru.grigorii.NauJava.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.entity.Role;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.service.exception.UserAlreadyExistsException;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UserService registration test")
class UserServiceTest
{
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserServiceImplementation service;

    private User buildSavedUser() 
    {
        User user = new User();
        user.setId(10L);
        user.setName("Grigorii");
        user.setSurname("Shcherbakov");
        user.setEmail("grisaserbakov0@gmail.com");
        user.setPasswordHash("hashed_pass");
        user.setTimezone(ZoneId.systemDefault().toString());
        user.setRole(Role.ADMIN);
        return user;
    }

    @Test
    @DisplayName("Should register user")
    void shouldRegisterUserSuccessfully()
    {
        UserDto dto = UserDto.forRegistration("Grigorii", "Shcherbakov", "grisaserbakov0@gmail.com", "pass123");

        when(repository.existsByEmail(dto.email())).thenReturn(false);
        when(encoder.encode("pass123")).thenReturn("hashed_pass");

        User savedUser = buildSavedUser();
        when(repository.save(any(User.class))).thenReturn(savedUser);

        UserDto result = service.register(dto);

        assertEquals(10L, result.id());
        assertEquals("Grigorii", result.name());
        assertEquals("Shcherbakov", result.surname());
        assertEquals("grisaserbakov0@gmail.com", result.email());
        assertEquals("hashed_pass", result.password());
        assertEquals(ZoneId.systemDefault(), result.timezone());

        verify(repository).existsByEmail("grisaserbakov0@gmail.com");
        verify(encoder).encode("pass123");
        verify(repository).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw exception when email already used")
    void shouldThrowIfEmailAlreadyExists() 
    {
        UserDto dto = UserDto.forRegistration("Grigorii", "Shcherbakov", "grisaserbakov0@gmail.com", "pass123");

        when(repository.existsByEmail(dto.email())).thenReturn(true);

        UserAlreadyExistsException ex = assertThrows(
                UserAlreadyExistsException.class,
                () -> service.register(dto)
        );

        assertEquals("User with email=grisaserbakov0@gmail.com already exists", ex.getMessage());

        verify(repository).existsByEmail("grisaserbakov0@gmail.com");
        verify(repository, never()).save(any());
        verify(encoder, never()).encode(any());
    }
}
