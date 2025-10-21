package ru.grigorii.NauJava.dto;

import ru.grigorii.NauJava.entity.User;

import java.time.ZoneId;

/**
 * DTO для пользователя с фабричными методами для конструирования экземпляров для определённых сценариев
 * @param id
 * @param name
 * @param surname
 * @param email
 * @param password
 * @param timezone
 */
public record UserDto(
        Long id,
        String name,
        String surname,
        String email,
        String password,
        ZoneId timezone
)
{
    public static UserDto forRegistration(String name, String surname, String email, String password)
    {
        return new UserDto(null, name, surname, email, password, null);
    }

    public static UserDto forDetailsUpdate(Long id, String name, String surname)
    {
        return new UserDto(id, name, surname, null, null, null);
    }

    public static UserDto forEmailUpdate(Long id, String email)
    {
        return new UserDto(id, null, null, email, null, null);
    }

    public static UserDto forPasswordUpdate(Long id, String password)
    {
        return new UserDto(id, null, null, null, password, null);
    }

    public static UserDto fromEntity(User entity)
    {
        return new UserDto(entity.getId(), entity.getName(), entity.getSurname(),
                entity.getEmail(), entity.getPasswordHash(), ZoneId.of(entity.getTimezone()));
    }
}