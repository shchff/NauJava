package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.service.user.UserService;

/**
 * Команда для получения всех пользователей
 */
@Component
public class GetAllUsersCommand implements Command
{
    private final UserService userService;

    @Autowired
    public GetAllUsersCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "get_all";
    }

    @Override
    public String usage()
    {
        return "get_all";
    }

    @Override
    public int paramsNumber()
    {
        return 0;
    }

    @Override
    public void execute(String[] args)
    {
        userService.findAll()
                .forEach(System.out::println);
    }
}
