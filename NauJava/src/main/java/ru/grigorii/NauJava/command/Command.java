package ru.grigorii.NauJava.command;

/**
 * Интерфейс команды
 */
public interface Command
{
    String name();
    String usage();
    int paramsNumber();
    void execute(String[] args);
}
