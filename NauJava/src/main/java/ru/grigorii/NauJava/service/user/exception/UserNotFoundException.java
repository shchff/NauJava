package ru.grigorii.NauJava.service.user.exception;

public class UserNotFoundException extends ServiceException
{
    private static final String message = "User with id=%d not found";

    public UserNotFoundException(Long id, Throwable cause)
    {
        super(String.format(message, id), cause);
    }
}
