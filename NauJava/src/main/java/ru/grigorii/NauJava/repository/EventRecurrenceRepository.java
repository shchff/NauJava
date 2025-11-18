package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.grigorii.NauJava.entity.EventRecurrence;

/**
 * Репозиторий для работы с сущностью повторение событий
 */
@RepositoryRestResource(path = "recurrences")
public interface EventRecurrenceRepository extends CrudRepository<EventRecurrence, Long>
{
}
