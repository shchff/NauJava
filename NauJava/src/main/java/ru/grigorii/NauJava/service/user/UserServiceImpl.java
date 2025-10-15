package ru.grigorii.NauJava.service.user;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.config.GeneralConfig;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.repository.exception.EntityExistsException;
import ru.grigorii.NauJava.repository.exception.EntityNotFoundException;
import ru.grigorii.NauJava.service.user.exception.UserAlreadyExistsException;
import ru.grigorii.NauJava.service.user.exception.UserNotFoundException;
import java.time.ZoneId;
import java.util.List;

/**
 * Реализация сервиса для работы с пользователями.
 * После создания выводит информацию о приложении в консоль. Использует idGeneratorWithIncrement для генерации
 * идентификаторов.
 */
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
    public Long register(UserDto dto)
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
    public UserDto findById(Long id)
    {
        return UserDto.fromEntity(findEntityById(id));
    }

    @Override
    public List<UserDto> findAll()
    {
        return repository.readAll()
                .stream()
                .map(UserDto::fromEntity)
                .toList();
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
    public void updateDetails(UserDto dto)
    {
        User user = findEntityById(dto.id());

        user.setName(dto.name());
        user.setSurname(dto.surname());

        repository.update(user);
    }

    @Override
    public void updateEmail(UserDto dto)
    {
        User user = findEntityById(dto.id());

        String email = dto.email();

        if (userWithEmailExists(email))
        {
            throw new UserAlreadyExistsException(email);
        }

        user.setEmail(dto.email());

        repository.update(user);
    }

    @Override
    public void updatePassword(UserDto dto)
    {
        User user = findEntityById(dto.id());

        user.setPassword(dto.password());

        repository.update(user);
    }

    private User findEntityById(Long id)
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
