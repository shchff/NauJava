package ru.grigorii.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.entity.Role;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.service.exception.UserAlreadyExistsException;
import java.time.ZoneId;
import java.util.Collection;
import java.util.List;

/**
 * Реализация сервиса пользователя и UserDetailsService с поправкой на то, что username это email
 */
@Service
public class UserServiceImplementation implements UserDetailsService, UserService
{
    private final PasswordEncoder encoder;
    private final UserRepository repository;

    @Autowired
    public UserServiceImplementation(PasswordEncoder encoder, UserRepository repository)
    {
        this.encoder = encoder;
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException
    {
        User appUser = repository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException(String.format("User with email %s not found", email))
        );

        return new org.springframework.security.core.userdetails.User(appUser.getEmail(), appUser.getPasswordHash(),
                mapRole(appUser));
    }

    private Collection<GrantedAuthority> mapRole(User appUser)
    {
        return List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole()));
    }

    @Override
    public UserDto register(UserDto dto)
    {
        String email = dto.email();

        if (userWithEmailExists(email))
        {
            throw new UserAlreadyExistsException(email);
        }

        User user = new User();

        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(email);
        user.setRole(Role.ADMIN);
        user.setPasswordHash(encoder.encode(dto.password()));
        user.setTimezone(ZoneId.systemDefault().toString());

        User savedUser = repository.save(user);

        return UserDto.fromEntity(savedUser);
    }

    private boolean userWithEmailExists(String email)
    {
        if (email == null)
        {
            return false;
        }

        return repository.existsByEmail(email);
    }
}
