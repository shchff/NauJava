package ru.grigorii.NauJava.console.command;

/**
 * Дополнительный интерфейс для реализаций Command, в которых нужно парсить id из String в Long
 */
public interface IdExtractableCommand extends Command
{
    default Long extractId(String idStr)
    {
        try
        {
            return Long.valueOf(idStr);
        }
        catch (NumberFormatException e)
        {
            throw new IllegalArgumentException("id must be Long", e);
        }
    }
}
