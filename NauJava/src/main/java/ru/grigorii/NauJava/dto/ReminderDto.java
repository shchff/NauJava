package ru.grigorii.NauJava.dto;

import java.time.ZonedDateTime;

public record ReminderDto(
        Long id,
        Long eventId,
        Long userId,
        ZonedDateTime remindAt,
        boolean sent,
        String channel
)
{
    /**
     * Фабричный метод для сохранения напоминания совместно с событием
     */
    public static ReminderDto toStoreWithEvent(ZonedDateTime remindAt)
    {
        return new ReminderDto(null, null, null, remindAt, false, null);
    }
}
