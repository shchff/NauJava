package ru.grigorii.NauJava.service;

import ru.grigorii.NauJava.dto.EventDto;
import ru.grigorii.NauJava.dto.ReminderDto;

/**
 * Сервис для бизнес-логики событий
 */
public interface EventService
{
    EventDto createEventWithReminder(EventDto eventDto, ReminderDto reminderDto);
}
