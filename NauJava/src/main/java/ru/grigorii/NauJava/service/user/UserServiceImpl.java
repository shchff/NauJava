package ru.grigorii.NauJava.service.user;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.config.GeneralConfig;
import ru.grigorii.NauJava.dto.user.RegisterUserDto;
import ru.grigorii.NauJava.dto.user.UpdateUserDetailsDto;
import ru.grigorii.NauJava.dto.user.UpdateUserEmailDto;
import ru.grigorii.NauJava.dto.user.UpdateUserPasswordDto;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.repository.exception.EntityExistsException;
import ru.grigorii.NauJava.repository.exception.EntityNotFoundException;
import ru.grigorii.NauJava.service.user.exception.UserAlreadyExistsException;
import ru.grigorii.NauJava.service.user.exception.UserNotFoundException;

import java.time.ZoneId;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    private final UserRepository repository;
    private final IdGenerator idGenerator;
    private final GeneralConfig config;

    @Autowired
    public UserServiceImpl(UserRepository repository, IdGenerator idGenerator, GeneralConfig config)
    {
        this.repository = repository;
        this.idGenerator = idGenerator;
        this.config = config;
    }

    @PostConstruct
    public void init()
    {
        System.out.println("Service initialized!");
        System.out.printf("App name: %s%n", config.getAppName());
        System.out.printf("App version: %s%n", config.getAppVersion());
    }

    @Override
    public Long register(RegisterUserDto dto)
    {
        String email = dto.email();

        if (userWithEmailExists(email))
        {
            throw new UserAlreadyExistsException(email);
        }

        User user = new User();

        Long id = idGenerator.generateId();

        user.setId(id);
        user.setName(dto.name());
        user.setSurname(dto.surname());
        user.setEmail(email);
        user.setPassword(dto.password());
        user.setTimezone(ZoneId.systemDefault());

        try
        {
            repository.create(user);
        }
        catch (EntityExistsException e)
        {
            throw new UserAlreadyExistsException(id, e);
        }

        return id;
    }

    @Override
    public User findById(Long id)
    {
        try
        {
            return repository.read(id);
        }
        catch (EntityNotFoundException e)
        {
            throw new UserNotFoundException(id, e);
        }
    }

    @Override
    public List<User> findAll()
    {
        return (List<User>) repository.readAll();
    }

    @Override
    public void deleteById(Long id)
    {
        try
        {
            repository.delete(id);
        }
        catch (EntityNotFoundException e)
        {
            throw new UserNotFoundException(id, e);
        }
    }

    @Override
    public void updateDetails(UpdateUserDetailsDto dto)
    {
        User user = findById(dto.id());

        user.setName(dto.name());
        user.setSurname(dto.surname());
    }

    @Override
    public void updateEmail(UpdateUserEmailDto dto)
    {
        User user = findById(dto.id());

        String email = dto.email();

        if (userWithEmailExists(email))
        {
            throw new UserAlreadyExistsException(email);
        }

        user.setEmail(dto.email());
    }

    @Override
    public void updatePassword(UpdateUserPasswordDto dto)
    {
        User user = findById(dto.id());

        user.setPassword(dto.password());
    }

    private boolean userWithEmailExists(String email)
    {
        if (email == null)
        {
            return false;
        }

        return repository.readAll()
                .stream()
                .anyMatch(u -> email.equals(u.getEmail()));
    }
}
