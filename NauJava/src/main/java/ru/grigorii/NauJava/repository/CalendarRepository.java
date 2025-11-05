package ru.grigorii.NauJava.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.Calendar;
import java.util.List;

/**
 * Репозиторий для работы с сущностью календарь
 */
@Repository
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