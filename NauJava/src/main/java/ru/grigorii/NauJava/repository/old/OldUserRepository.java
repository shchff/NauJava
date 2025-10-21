package ru.grigorii.NauJava.repository.old;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.old.exception.EntityExistsException;
import ru.grigorii.NauJava.repository.old.exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Репозиторий для работы с сущностью пользователь. Реализует как CRUD операции,
 * так и операцию чтения всех сущностей
 */
@Component
public class OldUserRepository implements OldCrudRepository<User, Long>, OldReadAllRepository<User>
{
    private final Map<Long, User> userContainer;

    @Autowired
    public OldUserRepository(Map<Long, User> userContainer)
    {
        this.userContainer = userContainer;
    }

    @Override
    public void create(User entity)
    {
        Long id = entity.getId();

        if (userExists(id))
        {
            throw new EntityExistsException(id);
        }

        userContainer.put(id, entity);
    }

    @Override
    public User read(Long id)
    {
        User user = userContainer.get(id);

        if (user == null)
        {
            throw new EntityNotFoundException(id);
        }

        return user;
    }

    @Override
    public List<User> readAll()
    {
        return new ArrayList<>(userContainer.values());
    }

    @Override
    public void update(User entity)
    {
        Long id = entity.getId();

        if (!userExists(id))
        {
            throw new EntityNotFoundException(id);
        }

        userContainer.put(id, entity);
    }

    @Override
    public void delete(Long id)
    {
        if (!userExists(id))
        {
            throw new EntityNotFoundException(id);
        }

        userContainer.remove(id);
    }

    private boolean userExists(Long id)
    {
        return userContainer.get(id) != null;
    }
}
