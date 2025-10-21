package ru.grigorii.NauJava.repository.custom;

import ru.grigorii.NauJava.entity.Reminder;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Кастомный репозиторий для сущности напоминание
 */
public interface ReminderRepositoryCustom
{
    /**
     * Возвращает список неотправленных напоминаний в указанный временной промежуток
     * @param from начало временного промежутка
     * @param to окончание временного промежутка
     */
    List<Reminder> findByRemindAtBetweenAndSentIsFalse(ZonedDateTime from, ZonedDateTime to);
}
