package ru.grigorii.NauJava.console.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.grigorii.NauJava.dto.UserDto;
import ru.grigorii.NauJava.service.user.UserService;

/**
 * Команда для регистрации пользователя
 */
@Component
public class RegisterUserCommand implements Command
{
    private final UserService userService;

    @Autowired
    public RegisterUserCommand(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public String name()
    {
        return "register";
    }

    @Override
    public String usage()
    {
        return "register <name> <surname> <email> <password>";
    }

    @Override
    public int paramsNumber()
    {
        return 4;
    }

    @Override
    public void execute(String[] args)
    {
        Long id = userService.register(
                UserDto.forRegistration(args[0], args[1], args[2], args[3]));

        System.out.printf("User registered successfully!%nUser id: %d%n", id);
    }
}
