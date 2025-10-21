package ru.grigorii.NauJava.service.old;

import ru.grigorii.NauJava.dto.UserDto;
import java.util.List;

/**
 * Интерфейс сервиса для реализации бизнес-логики для работы с пользователями
 */
public interface UserService
{
    Long register(UserDto dto);
    UserDto findById(Long id);
    List<UserDto> findAll();
    void deleteById(Long id);
    void updateDetails(UserDto dto);
    void updateEmail(UserDto dto);
    void updatePassword(UserDto dto);
}
