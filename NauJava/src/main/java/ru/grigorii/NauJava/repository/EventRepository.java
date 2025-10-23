package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.Event;

/**
 * Репозиторий для работы с сущностью событие
 */
@Repository
public interface EventRepository extends CrudRepository<Event, Long>
{
}
