package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

/**
 * Сущность повторение события
 */
@Entity
@Table(name = "event_recurrences")
public class EventRecurrence
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Событие, для которого производятся повторения
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false, unique = true)
    private Event event;

    /**
     * Дата начала повторений
     */
    @Column(name = "from_date", nullable = false)
    private ZonedDateTime fromDate;

    /**
     * Дата окончания повторений
     */
    @Column(name = "until_date")
    private ZonedDateTime endDate;

    /**
     * Правило повторения события, буду реализовывать через RRULE, RFC 5545.
     */
    @Column(name = "rrule", nullable = false)
    private String rrule;

    public Event getEvent()
    {
        return event;
    }

    public void setEvent(Event event)
    {
        this.event = event;
    }

    public ZonedDateTime getFromDate()
    {
        return fromDate;
    }

    public void setFromDate(ZonedDateTime fromDate)
    {
        this.fromDate = fromDate;
    }

    public ZonedDateTime getEndDate()
    {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate)
    {
        this.endDate = endDate;
    }

    public String getRrule()
    {
        return rrule;
    }

    public void setRrule(String rrule)
    {
        this.rrule = rrule;
    }
}
