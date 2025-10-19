package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.user.UserService;

/**
 * Команда для обновления email пользователя
 */
@Component
public class ChangeUserEmailCommand implements IdExtractableCommand
{
    private final UserService userService;

    @Autowired
    public ChangeUserEmailCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "change_email";
    }

    @Override
    public String usage()
    {
        return "change_email <id> <email>";
    }

    @Override
    public int paramsNumber()
    {
        return 2;
    }

    @Override
    public void execute(String[] args)
    {
        Long id = extractId(args[0]);
        userService.updateEmail(UserDto.forEmailUpdate(id, args[1]));
        System.out.println("Email changed successfully!");
    }
}
