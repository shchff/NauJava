package ru.grigorii.NauJava.service.user.exception;

/**
 * Исключение, связанное с отсутствием в БД пользователя с указанным id
 */
public class UserNotFoundException extends ServiceException
{
    private static final String message = "User with id=%d not found";

    public UserNotFoundException(Long id, Throwable cause)
    {
        super(String.format(message, id), cause);
    }
}
