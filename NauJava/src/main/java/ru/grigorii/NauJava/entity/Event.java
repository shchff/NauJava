package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Сущность событие
 */
@Entity
@Table(name = "events")
public class Event
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название
     */
    @Column(length = 200, nullable = false)
    private String title;

    /**
     * Описание
     */
    private String description;

    /**
     * Время начала <br/>
     * Трактовка 1: если событие может произойти в любой момент дня, то хранится время с точностью до дня <br/>
     * Трактовка 2: если событие должно произойти в определённый момент, то хранится время с точностью до минут
     */
    @Column(name = "start_time", nullable = false)
    private ZonedDateTime startTime;

    /**
     * Время окончания
     */
    @Column(name = "end_time")
    private ZonedDateTime endTime;

    /**
     * Приоритет события
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private EventPriority priority = EventPriority.MEDIUM;

    /**
     * Выполнено ли событие
     */
    @Column(nullable = false)
    private Boolean done = false;

    /**
     * Календарь, в котором находится событие
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    /**
     * Создатель события
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    /**
     * Время создания события
     */
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    /**
     * Правило повторения события
     */
    @OneToOne(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private EventRecurrence recurrence;

    /**
     * Напоминания события
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reminder> reminders;

    /**
     * Фабричный метод для создания события с необходимыми полями
     */
    public static Event requiredFields(String title, ZonedDateTime startTime, Calendar calendar, User createdBy)
    {
        Event event = new Event();
        event.setTitle(title);
        event.setStartTime(startTime);
        event.setCalendar(calendar);
        event.setCreatedBy(createdBy);

        return event;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public ZonedDateTime getStartTime()
    {
        return startTime;
    }

    public void setStartTime(ZonedDateTime startTime)
    {
        this.startTime = startTime;
    }

    public ZonedDateTime getEndTime()
    {
        return endTime;
    }

    public void setEndTime(ZonedDateTime endTime)
    {
        this.endTime = endTime;
    }

    public EventPriority getPriority()
    {
        return priority;
    }

    public void setPriority(EventPriority priority)
    {
        this.priority = priority;
    }

    public Boolean getDone()
    {
        return done;
    }

    public void setDone(Boolean done)
    {
        this.done = done;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public User getCreatedBy()
    {
        return createdBy;
    }

    public void setCreatedBy(User createdBy)
    {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public EventRecurrence getRecurrence()
    {
        return recurrence;
    }

    public void setRecurrence(EventRecurrence recurrence)
    {
        this.recurrence = recurrence;
    }
}
