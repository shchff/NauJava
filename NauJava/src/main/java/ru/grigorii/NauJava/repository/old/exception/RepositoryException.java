package ru.grigorii.NauJava.repository.old.exception;

/**
 * Исключение слоя взаимодействия с БД
 */
public class RepositoryException extends RuntimeException
{
    public RepositoryException(String message)
    {
        super(message);
    }
}
