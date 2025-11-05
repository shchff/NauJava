package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Сущность пользователь
 */
@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя
     */
    @Column(length = 50)
    private String name;

    /**
     * Фамилия
     */
    @Column(length = 50)
    private String surname;

    /**
     * Электронная почта
     */
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    /**
     * Хэш пароля
     */
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    /**
     * Роль пользователя
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    /**
     * Временная зона пользователя
     */
    @Column(length = 75)
    private String timezone;

    /**
     * Время создания пользователя
     */
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    /**
     * Календари, в которых пользователя является участником
     */
    @OneToMany(mappedBy = "member")
    private Set<CalendarMembership> calendars;

    /**
     * Напоминания, которые нужно отправить / отправленные пользователю
     */
    @OneToMany(mappedBy = "user")
    private Set<Reminder> reminders;

    /**
     * Фабричный метод для создания пользователя с необходимыми полями
     */
    public static User requiredFields(String email, String passwordHash, String timezone)
    {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        user.setTimezone(timezone);

        return user;
    }

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

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public void setTimezone(String timezone)
    {
        this.timezone = timezone;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }
}
