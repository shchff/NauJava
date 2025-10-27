package ru.grigorii.NauJava.dto;

import ru.grigorii.NauJava.entity.Reminder;
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

    /**
     * Фабричный метод, возвращающий DTO из сущности
     */
    public static ReminderDto fromEntity(Reminder entity)
    {
        return new ReminderDto(entity.getId(), entity.getEvent().getId(), entity.getUser().getId(),
                entity.getRemindAt(), entity.getSent(), entity.getChannel().toString());
    }
}
