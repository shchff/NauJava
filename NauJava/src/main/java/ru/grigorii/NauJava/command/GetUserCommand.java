package ru.grigorii.NauJava.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.user.UserService;

@Component
public class GetUserCommand implements IdExtractableCommand
{
    private final UserService userService;

    @Autowired
    public GetUserCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "get";
    }

    @Override
    public String usage()
    {
        return "get <id>";
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
        UserDto user = userService.findById(id);
        System.out.println(user);
    }
}
