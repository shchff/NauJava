package ru.grigorii.NauJava.entity;

import java.time.ZoneId;

/**
 * Сущность пользователь
 */
public class User
{
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private ZoneId timezone;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public ZoneId getTimezone()
    {
        return timezone;
    }

    public void setTimezone(ZoneId timezone)
    {
        this.timezone = timezone;
    }

    @Override
    public String toString()
    {
        return String.format(
                "User{id=%s, name='%s', surname='%s', email='%s', password='%s', timezone=%s}",
                id, name, surname, email, password, timezone
        );
    }
}
