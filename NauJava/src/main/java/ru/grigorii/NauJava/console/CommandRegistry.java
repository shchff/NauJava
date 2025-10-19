package ru.grigorii.NauJava.console;

import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.console.command.Command;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Агрегатор всех классов, реализующих интерфейс Command
 */
@Component
public class CommandRegistry
{
    private final Map<String, Command> commands;

    public CommandRegistry(List<Command> commands)
    {
        this.commands = commands.stream()
                .collect(Collectors.toMap(
                        Command::name,
                        c -> c,
                        (a, b) -> a,
                        TreeMap::new
                ));
    }

    public Command get(String name)
    {
        return commands.get(name);
    }

    public Collection<Command> all()
    {
        return commands.values();
    }
}
