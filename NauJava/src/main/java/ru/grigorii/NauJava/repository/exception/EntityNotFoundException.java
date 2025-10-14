package ru.grigorii.NauJava.repository.exception;

public class EntityNotFoundException extends RepositoryException
{
    private static final String message = "Entity with id=%d not found";

    public EntityNotFoundException(Long id)
    {
        super(String.format(message, id));
    }
}
