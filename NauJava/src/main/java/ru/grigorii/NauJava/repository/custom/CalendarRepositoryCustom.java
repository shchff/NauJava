package ru.grigorii.NauJava.repository.custom;

import ru.grigorii.NauJava.entity.Calendar;
import java.util.List;

/**
 * Кастомный репозиторий для сущности календарь
 */
public interface CalendarRepositoryCustom
{
    /**
     * Возвращает все календари, в которых участвует указанный пользователь.
     * @param userId идентификатор пользователя
     */
    List<Calendar> findAllByUserId(Long userId);
}
