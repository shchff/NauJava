package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.console.CommandRegistry;

/**
 * Команда для вывода инструкций для всех команд
 */
@Component
public class HelpCommand implements Command
{
    private final CommandRegistry commands;

    @Autowired
    public HelpCommand(@Lazy CommandRegistry commands)
    {
        this.commands = commands;
    }

    @Override
    public String name()
    {
        return "help";
    }

    @Override
    public String usage()
    {
        return "help";
    }

    @Override
    public int paramsNumber()
    {
        return 0;
    }

    @Override
    public void execute(String[] args)
    {
        System.out.println("Commands usage:\n");

        commands.all().forEach(c -> System.out.println(c.usage()));
    }
}
