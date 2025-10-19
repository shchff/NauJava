package ru.grigorii.NauJava.console.command;

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
