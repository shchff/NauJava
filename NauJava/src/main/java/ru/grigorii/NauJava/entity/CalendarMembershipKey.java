package ru.grigorii.NauJava.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * Составной ключ для сущности участия в календаре
 */
@Embeddable
public class CalendarMembershipKey implements Serializable
{
    @Column(name = "calendar_id")
    private Long calendarId;

    @Column(name = "member_id")
    private Long memberId;

    public CalendarMembershipKey(Long calendarId, Long memberId)
    {
        this.calendarId = calendarId;
        this.memberId = memberId;
    }

    public CalendarMembershipKey() {}

    public Long getCalendarId()
    {
        return calendarId;
    }

    public void setCalendarId(Long calendarId)
    {
        this.calendarId = calendarId;
    }

    public Long getMemberId()
    {
        return memberId;
    }

    public void setMemberId(Long memberId)
    {
        this.memberId = memberId;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == null || getClass() != o.getClass()) return false;

        CalendarMembershipKey that = (CalendarMembershipKey) o;
        return calendarId.equals(that.calendarId) && memberId.equals(that.memberId);
    }

    @Override
    public int hashCode()
    {
        int result = calendarId.hashCode();
        result = 31 * result + memberId.hashCode();
        return result;
    }
}
