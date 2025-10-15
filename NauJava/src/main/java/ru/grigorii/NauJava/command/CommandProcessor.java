package ru.grigorii.NauJava.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.service.user.exception.ServiceException;

/**
 * Обработчик команд. Осуществляются дополнительные проверки на корректность ввода
 * и ведётся глобальная обработка ошибок
 */
@Component
public class CommandProcessor
{
    private final CommandRegistry commands;

    @Autowired
    public CommandProcessor(CommandRegistry commands)
    {
        this.commands = commands;
    }

    public void processCommand(String input)
    {
        String[] cmd = input.split(" ");

        if (cmd.length > 0 && !cmd[0].isEmpty())
        {
            String name = cmd[0].toLowerCase();
            Command command = commands.get(name);

            if (command == null)
            {
                System.out.println("There is no such command\nEnter 'help' to check the usage");
                return;
            }

            String[] params = new String[cmd.length - 1];
            System.arraycopy(cmd, 1, params, 0, params.length);

            if (params.length != command.paramsNumber())
            {
                System.out.printf("Wrong number of params for this command%nUsage: %s%n",
                        command.usage());
                return;
            }

            try
            {
                command.execute(params);
            }
            catch (IllegalArgumentException e)
            {
                System.out.printf("Illegal argument: %s%n", e.getMessage());
            }
            catch (ServiceException e)
            {
                System.out.printf("Service exception occurred: %s%n", e.getMessage());
            }
            catch (Exception e)
            {
                System.out.printf("Unknown exception occurred: %s%n", e.getMessage());
            }
        }
    }
}
