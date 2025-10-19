package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.service.user.UserService;

/**
 * Команда для удаления пользователя
 */
@Component
public class DeleteUserCommand implements IdExtractableCommand
{
    private final UserService userService;

    @Autowired
    public DeleteUserCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "delete";
    }

    @Override
    public String usage()
    {
        return "delete <id>";
    }

    @Override
    public int paramsNumber()
    {
        return 1;
    }

    @Override
    public void execute(String[] args)
    {
        Long id = extractId(args[0]);
        userService.deleteById(id);
        System.out.println("User deleted successfully!");
    }
}
