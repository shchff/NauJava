package ru.grigorii.NauJava.service;

import ru.grigorii.NauJava.dto.ReminderDto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сервис напоминаний
 */
public interface ReminderService
{
    List<ReminderDto> findAllBetweenUnsent(ZonedDateTime from, ZonedDateTime to);
}
