package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.User;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью пользователь
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long>
{
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
