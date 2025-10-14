package ru.grigorii.NauJava.command.exception;

public class NoSuchCommandException extends RuntimeException
{
    private static final String message = "No such command %s";

    public NoSuchCommandException(String command)
    {
        super(String.format(message, command));
    }
}
