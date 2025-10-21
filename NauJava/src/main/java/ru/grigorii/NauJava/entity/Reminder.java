package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

/**
 * Сущность напоминание
 */
@Entity
@Table(name = "reminder")
public class Reminder
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Событие, о котором нужно напомнить
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    /**
     * Пользователь, которому нужно отправить напоминание
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Когда отправить напоминание
     */
    @Column(name = "remind_at", nullable = false)
    private ZonedDateTime remindAt;

    /**
     * Отправлено ли напоминание
     */
    @Column(nullable = false)
    private Boolean sent = false;

    /**
     * Канал отправки напоминания
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private ReminderChannel channel = ReminderChannel.PUSH;

    /**
     * Фабричный метод для создания напоминания с необходимыми полями
     */
    public static Reminder requiredFields(Event event, User user, ZonedDateTime remindAt)
    {
        Reminder reminder = new Reminder();
        reminder.setEvent(event);
        reminder.setUser(user);
        reminder.setRemindAt(remindAt);

        return reminder;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public ZonedDateTime getRemindAt()
    {
        return remindAt;
    }

    public void setRemindAt(ZonedDateTime remindAt)
    {
        this.remindAt = remindAt;
    }

    public Boolean getSent()
    {
        return sent;
    }

    public void setSent(Boolean sent)
    {
        this.sent = sent;
    }

    public ReminderChannel getChannel()
    {
        return channel;
    }

    public void setChannel(ReminderChannel channel)
    {
        this.channel = channel;
    }
}
