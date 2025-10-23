package ru.grigorii.NauJava.repository.old.exception;

/**
 * Исключение, возникающее при попытке создать сущность с указанным id
 */
public class EntityExistsException extends RepositoryException
{
    private static final String message = "Entity with id=%d already exists";

    public EntityExistsException(Long id)
    {
        super(String.format(message, id));
    }
}
