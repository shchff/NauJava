package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.grigorii.NauJava.entity.User;

/**
 * Репозиторий для работы с сущностью пользователь
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long>
{
}
