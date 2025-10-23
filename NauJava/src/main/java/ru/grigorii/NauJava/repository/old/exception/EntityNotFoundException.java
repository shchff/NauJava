package ru.grigorii.NauJava.repository.old.exception;

/**
 * Исключение, связанное с отсутствием в БД сущности с указанным id
 */
public class EntityNotFoundException extends RepositoryException
{
    private static final String message = "Entity with id=%d not found";

    public EntityNotFoundException(Long id)
    {
        super(String.format(message, id));
    }
}
