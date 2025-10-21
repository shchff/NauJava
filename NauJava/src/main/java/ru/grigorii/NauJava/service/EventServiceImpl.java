package ru.grigorii.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.grigorii.NauJava.dto.EventDto;
import ru.grigorii.NauJava.dto.ReminderDto;
import ru.grigorii.NauJava.entity.*;
import ru.grigorii.NauJava.repository.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Реализация сервиса бизнес-логики событий
 */
public class EventServiceImpl implements EventService
{
    private final EventRepository eventRepository;
    private final ReminderRepository reminderRepository;
    private final CalendarMembershipRepository membershipRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, ReminderRepository reminderRepository,
                            CalendarMembershipRepository membershipRepository, CalendarRepository calendarRepository,
                            UserRepository userRepository)
    {
        this.eventRepository = eventRepository;
        this.reminderRepository = reminderRepository;
        this.membershipRepository = membershipRepository;
        this.calendarRepository = calendarRepository;
        this.userRepository = userRepository;
    }

    /**
     * При создании нового события так же создаются напоминания о событии для всех участников
     * календаря
     * @param eventDto dto события
     * @param reminderDto dto напоминания
     */
    @Override
    @Transactional
    public EventDto createEventWithReminder(EventDto eventDto, ReminderDto reminderDto)
    {
        Calendar calendar = calendarRepository.findById(eventDto.calendarId())
                .orElseThrow(() -> new IllegalArgumentException("Calendar is not found"));

        // Для демонстрации тестирования метода сделал "плохо"
        // TODO: раскомментировать проброс ошибки
        User creator = userRepository.findById(eventDto.createdBy()).get();
        //        .orElseThrow(() -> new IllegalArgumentException("User is not found"));

        Event event = new Event();
        event.setCalendar(calendar);
        event.setTitle(eventDto.title());
        event.setDescription(eventDto.description());
        event.setStartTime(eventDto.startTime());
        event.setEndTime(eventDto.endTime());
        event.setCreatedBy(creator);

        Event savedEvent = eventRepository.save(event);

        List<CalendarMembership> memberships = membershipRepository.findAllByCalendarId(calendar.getId());

        List<Reminder> reminders = new ArrayList<>();

        for (CalendarMembership membership : memberships)
        {
            Reminder reminder = new Reminder();
            reminder.setEvent(savedEvent);
            reminder.setUser(membership.getMember());
            reminder.setRemindAt(reminderDto.remindAt());
            reminder.setSent(false);

            reminders.add(reminder);
        }

        reminderRepository.saveAll(reminders);

        return EventDto.fromEntity(event);
    }
}
