package ru.grigorii.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.grigorii.NauJava.entity.Calendar;
import java.util.List;

/**
 * Репозиторий для работы с сущностью календарь
 */
@RepositoryRestResource(path = "calendars")
public interface CalendarRepository extends CrudRepository<Calendar, Long>
{
    /**
     * Возвращает все календари, в которых участвует указанный пользователь.
     * @param userId идентификатор пользователя
     */
    @Query("""
            SELECT c FROM Calendar c
            JOIN c.members m
            WHERE m.member.id = :userId
            """)
    List<Calendar> findAllByUserId(Long userId);
}