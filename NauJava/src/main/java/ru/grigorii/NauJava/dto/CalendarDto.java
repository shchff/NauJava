package ru.grigorii.NauJava.dto;

import ru.grigorii.NauJava.entity.Calendar;
import java.time.ZonedDateTime;

public record CalendarDto(
        Long id,
        String name,
        String description,
        boolean shared,
        Long ownerId,
        ZonedDateTime createdAt
)
{
    /**
     * Фабричный метод, возвращающий DTO из сущности
     */
    public static CalendarDto fromEntity(Calendar entity)
    {
        return new CalendarDto(entity.getId(), entity.getName(), entity.getDescription(),
                entity.isShared(), entity.getOwner().getId(), entity.getCreatedAt());
    }
}
