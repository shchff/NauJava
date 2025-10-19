package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.user.UserService;

/**
 * Команда для обновления пароля пользователя
 */
@Component
public class ChangeUserPasswordCommand implements IdExtractableCommand
{
    private final UserService userService;

    @Autowired
    public ChangeUserPasswordCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "change_password";
    }

    @Override
    public String usage()
    {
        return "change_password <id> <password>";
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
        userService.updatePassword(UserDto.forPasswordUpdate(id, args[1]));
        System.out.println("Password changed successfully!");
    }
}
