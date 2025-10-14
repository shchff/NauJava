package ru.grigorii.NauJava.command;

import ru.grigorii.NauJava.command.exception.NoSuchCommandException;

import java.util.Arrays;

public enum Command
{
    REGISTER("register", "register <name> <surname> <email> <password>", 4),
    GET("get", "get <id>", 1),
    GET_ALL("get_all", "get_all", 0),
    DELETE("delete", "delete <id>", 1),
    CHANGE_DETAILS("change_details", "change_details <id> <name> <surname>", 3),
    CHANGE_EMAIL("change_email", "change_email <id> <email>", 2),
    CHANGE_PASSWORD("change_password", "change_password <id> <password>", 2),
    HELP("help", "help", 0);

    private final String name;
    private final String usage;
    private final int paramsNumber;

    Command(String name, String usage, int paramsNumber)
    {
        this.name = name;
        this.usage = usage;
        this.paramsNumber = paramsNumber;
    }

    public static Command fromCommandName(String name)
    {
        return Arrays.stream(values())
                .filter(c -> name.equalsIgnoreCase(c.name))
                .findAny()
                .orElseThrow(() -> new NoSuchCommandException(name));
    }

    public String getName()
    {
        return name;
    }

    public String getUsage()
    {
        return usage;
    }

    public int getParamsNumber()
    {
        return paramsNumber;
    }

}
