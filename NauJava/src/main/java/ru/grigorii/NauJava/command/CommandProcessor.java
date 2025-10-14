package ru.grigorii.NauJava.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.command.exception.NoSuchCommandException;
import ru.grigorii.NauJava.dto.user.RegisterUserDto;
import ru.grigorii.NauJava.dto.user.UpdateUserDetailsDto;
import ru.grigorii.NauJava.dto.user.UpdateUserEmailDto;
import ru.grigorii.NauJava.dto.user.UpdateUserPasswordDto;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.service.user.UserService;
import ru.grigorii.NauJava.service.user.exception.ServiceException;

import java.util.Arrays;

@Component
public class CommandProcessor
{
    private final UserService userService;

    @Autowired
    public CommandProcessor(UserService userService)
    {
        this.userService = userService;
    }

    public void processCommand(String input)
    {
        String[] cmd = input.split(" ");

        if (cmd.length > 0 && !cmd[0].isEmpty())
        {
            try
            {
                Command command = Command.fromCommandName(cmd[0]);

                if (checkParamsNumber(cmd, command))
                {
                    switch (command)
                    {
                        case REGISTER -> register(cmd);
                        case GET -> get(cmd);
                        case GET_ALL -> get_all();
                        case CHANGE_DETAILS -> change_details(cmd);
                        case CHANGE_EMAIL -> change_email(cmd);
                        case CHANGE_PASSWORD -> change_password(cmd);
                        case DELETE -> delete(cmd);
                        case HELP -> help();
                    }
                }
                else
                {
                    printCommandUsage(command);
                }
            }
            catch (NoSuchCommandException e)
            {
                System.out.println("There is no such command\nEnter 'help' to check the usage");
            }
            catch (IllegalArgumentException e)
            {
                System.out.printf("Illegal argument: %s%n", e.getMessage());
            }
            catch (ServiceException e)
            {
                System.out.printf("Service exception occurred: %s%n", e.getMessage());
            }
        }
    }

    private void register(String[] cmd)
    {
        Long id = userService.register(
                new RegisterUserDto(cmd[1], cmd[2], cmd[3], cmd[4]));

        System.out.printf("User registered successfully!%nUser id: %d%n", id);
    }

    private void get(String[] cmd)
    {
        Long id = extractId(cmd[1]);
        User user = userService.findById(id);
        System.out.println(user);
    }

    private void get_all()
    {
        userService.findAll()
                .forEach(System.out::println);
    }

    private void change_details(String[] cmd)
    {
        Long id = extractId(cmd[1]);
        userService.updateDetails(new UpdateUserDetailsDto(id, cmd[2], cmd[3]));
        System.out.println("Details changed successfully!");

    }

    private void change_email(String[] cmd)
    {
        Long id = extractId(cmd[1]);
        userService.updateEmail(new UpdateUserEmailDto(id, cmd[2]));
        System.out.println("Email changed successfully!");
    }

    private void change_password(String[] cmd)
    {
        Long id = extractId(cmd[1]);
        userService.updatePassword(new UpdateUserPasswordDto(id, cmd[2]));
        System.out.println("Password changed successfully!");
    }

    private void delete(String[] cmd)
    {
        Long id = extractId(cmd[1]);
        userService.deleteById(id);
        System.out.println("User deleted successfully!");
    }

    private void help()
    {
        System.out.println("Commands usage:\n");

        Arrays.stream(Command.values())
                .forEach(c -> System.out.println(c.getUsage()));
    }

    private boolean checkParamsNumber(String[] cmd, Command command)
    {
        return cmd.length - 1 == command.getParamsNumber();
    }

    private Long extractId(String idStr)
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

    private void printCommandUsage(Command command)
    {
        System.out.printf("Command %s usage:\n%s%n", command.getName(), command.getUsage());
    }
}
