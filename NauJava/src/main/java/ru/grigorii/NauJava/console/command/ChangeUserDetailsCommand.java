package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.old.UserService;

/**
 * Команда для обновления имени и фамилии пользователя
 */
@Component
public class ChangeUserDetailsCommand implements IdExtractableCommand
{
    private final UserService userService;

    @Autowired
    public ChangeUserDetailsCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "change_details";
    }

    @Override
    public String usage()
    {
        return "change_details <id> <name> <surname";
    }

    @Override
    public int paramsNumber()
    {
        return 3;
    }

    @Override
    public void execute(String[] args)
    {
        Long id = extractId(args[0]);
        userService.updateDetails(UserDto.forDetailsUpdate(id, args[1], args[2]));
        System.out.println("Details changed successfully!");
    }
}
