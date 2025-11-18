package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.grigorii.NauJava.entity.Reminder;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью напоминание
 */
@RepositoryRestResource(path = "reminders")
public interface ReminderRepository extends CrudRepository<Reminder, Long>
{
    /**
     * Возвращает список неотправленных напоминаний в указанный временной промежуток
     * @param from начало временного промежутка
     * @param to окончание временного промежутка
     */
    List<Reminder> findByRemindAtBetweenAndSentIsFalse(ZonedDateTime from, ZonedDateTime to);
}
