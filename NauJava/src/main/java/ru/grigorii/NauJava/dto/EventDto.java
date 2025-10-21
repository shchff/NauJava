package ru.grigorii.NauJava.dto;

import ru.grigorii.NauJava.entity.Event;
import java.time.ZonedDateTime;

public record EventDto(
        Long id,
        String title,
        String description,
        ZonedDateTime startTime,
        ZonedDateTime endTime,
        String priority,
        boolean done,
        Long calendarId,
        Long createdBy,
        ZonedDateTime createdAt
)
{
    /**
     * Фабричный метод, возвращающий DTO из сущности
     */
    public static EventDto fromEntity(Event entity)
    {
        return new EventDto(entity.getId(), entity.getTitle(), entity.getDescription(),
                entity.getStartTime(), entity.getEndTime(), entity.getPriority().toString(),
                entity.getDone(), entity.getCalendar().getId(), entity.getCreatedBy().getId(), entity.getCreatedAt());
    }

    /**
     * Фабричный метод для сохранения события
     */
    public static EventDto toStore(String title, String description, ZonedDateTime startTime,
                                ZonedDateTime endTime, String priority, Long calendarId, Long createdBy)
    {
        return new EventDto(null, title, description, startTime,
                endTime, priority, false, calendarId, createdBy, ZonedDateTime.now());
    }
}
