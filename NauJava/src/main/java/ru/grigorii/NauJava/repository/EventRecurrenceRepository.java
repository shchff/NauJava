package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.EventRecurrence;

/**
 * Репозиторий для работы с сущностью повторение событий
 */
@Repository
public interface EventRecurrenceRepository extends CrudRepository<EventRecurrence, Long>
{
}
