package ru.grigorii.NauJava.service;

import ru.grigorii.NauJava.dto.UserDto;

/**
 * Сервис пользователя
 */
public interface UserService
{
    UserDto register(UserDto dto);
}
