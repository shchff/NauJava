package ru.grigorii.NauJava.service;

import ru.grigorii.NauJava.dto.CalendarDto;
import java.util.List;

/**
 * Сервис календарей
 */
public interface CalendarService
{
    List<CalendarDto> findAllCalendarsByUserId(Long userId);
}
