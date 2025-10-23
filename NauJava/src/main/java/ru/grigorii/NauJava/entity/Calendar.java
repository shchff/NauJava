package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Сущность календарь
 */
@Entity
@Table(name = "calendars")
public class Calendar
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Имя календаря
     */
    @Column(length = 100, nullable = false)
    private String name;

    /**
     * Описание календаря
     */
    private String description;

    /**
     * Является ли личным или совместным с другими пользователями
     */
    @Column(name = "is_shared", nullable = false)
    private boolean shared = false;

    /**
     * Владелец / создатель календаря
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    /**
     * Время создания календаря
     */
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    /**
     * Участники календаря
     */
    @OneToMany(mappedBy = "calendar")
    private Set<CalendarMembership> members;

    /**
     * События календаря
     */
    @OneToMany(mappedBy = "calendar", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event> events;

    /**
     * Фабричный метод для создания календаря с необходимыми полями
     */
    public static Calendar requiredFields(String name, User owner)
    {
        Calendar calendar = new Calendar();
        calendar.setName(name);
        calendar.setOwner(owner);

        return calendar;
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

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public boolean isShared()
    {
        return shared;
    }

    public void setShared(boolean shared)
    {
        this.shared = shared;
    }

    public User getOwner()
    {
        return owner;
    }

    public void setOwner(User owner)
    {
        this.owner = owner;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt)
    {
        this.createdAt = createdAt;
    }
}
