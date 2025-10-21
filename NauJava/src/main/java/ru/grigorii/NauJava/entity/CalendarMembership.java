package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;
import java.time.ZonedDateTime;

/**
 * Сущность участие в календаре
 */
@Entity
@Table(name = "calendar_membership")
public class CalendarMembership
{
    /**
     * Составной ключ
     */
    @EmbeddedId
    private CalendarMembershipKey id;

    /**
     * Календарь
     */
    @ManyToOne
    @MapsId("calendarId")
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    /**
     * Участник
     */
    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id", nullable = false)
    private User member;

    /**
     * Роль участника
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CalendarRole role;

    /**
     * Время создания участия
     */
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt = ZonedDateTime.now();

    /**
     * Фабричный метод для создания участия с необходимыми полями
     */
    public static CalendarMembership requiredFields(Calendar calendar, User member, CalendarRole role)
    {
        CalendarMembership membership = new CalendarMembership();
        membership.setId(new CalendarMembershipKey(calendar.getId(), member.getId()));
        membership.setCalendar(calendar);
        membership.setMember(member);
        membership.setRole(role);

        return membership;
    }

    public CalendarMembershipKey getId()
    {
        return id;
    }

    public void setId(CalendarMembershipKey id)
    {
        this.id = id;
    }

    public Calendar getCalendar()
    {
        return calendar;
    }

    public void setCalendar(Calendar calendar)
    {
        this.calendar = calendar;
    }

    public User getMember()
    {
        return member;
    }

    public void setMember(User member)
    {
        this.member = member;
    }

    public CalendarRole getRole()
    {
        return role;
    }

    public void setRole(CalendarRole role)
    {
        this.role = role;
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
