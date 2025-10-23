package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.User;

/**
 * Репозиторий для работы с сущностью пользователь
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
}
