package ru.grigorii.NauJava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.grigorii.NauJava.dto.EventDto;
import ru.grigorii.NauJava.dto.ReminderDto;
import ru.grigorii.NauJava.entity.*;
import ru.grigorii.NauJava.repository.*;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для сервиса событий. Тестируется транзакционная операция. Используется in-memory СУБД H2.
 */
@DisplayName("EventService test")
@DataJpaTest
@Import(EventServiceImpl.class)
public class EventServiceTest
{
    @Autowired
    private EventServiceImpl service;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private CalendarMembershipRepository membershipRepository;

    @Autowired
    private UserRepository userRepository;

    private Calendar calendar;
    private User creator;

    @BeforeEach
    void setup()
    {
        reminderRepository.deleteAll();
        eventRepository.deleteAll();
        membershipRepository.deleteAll();
        calendarRepository.deleteAll();
        userRepository.deleteAll();

        creator = userRepository.save(
                User.requiredFields(
                        "creator@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );

        User member1 = userRepository.save(
                User.requiredFields(
                        "member1@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );

        User member2 = userRepository.save(
                User.requiredFields(
                        "member2@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );

        calendar = calendarRepository.save(
                Calendar.requiredFields("Work", creator)
        );

        membershipRepository.saveAll(
                List.of(
                        CalendarMembership.requiredFields(calendar, creator, CalendarRole.OWNER),
                        CalendarMembership.requiredFields(calendar, member1, CalendarRole.EDITOR),
                        CalendarMembership.requiredFields(calendar, member2, CalendarRole.VIEWER)
                )
        );
    }

    @DisplayName("Transactional operation is successful")
    @Test
    void shouldCreateEventWithReminders() {

        EventDto eventDto = EventDto.toStore(
                "Meeting",
                "Planning project",
                ZonedDateTime.now().plusHours(1),
                ZonedDateTime.now().plusHours(2),
                "HIGH",
                calendar.getId(),
                creator.getId()
        );

        ReminderDto reminderDto = ReminderDto.toStoreWithEvent(ZonedDateTime.now().plusMinutes(30));

        EventDto result = service.createEventWithReminder(eventDto, reminderDto);

        assertNotNull(result.id(), "ID must be generated");
        assertEquals("Meeting", result.title());

        List<Event> events = (List<Event>) eventRepository.findAll();
        assertEquals(1, events.size());
        assertEquals(calendar.getId(), events.getFirst().getCalendar().getId());

        List<Reminder> reminders = (List<Reminder>) reminderRepository.findAll();
        assertEquals(3, reminders.size(), "Must be 3 reminders");

        for (Reminder r : reminders)
        {
            assertFalse(r.getSent(), "Reminders must not be sent");
            assertEquals(events.getFirst().getId(), r.getEvent().getId());
        }
    }

    @DisplayName("Transactional operation is rolled back")
    @Test
    void shouldNotCreateEventWithRemindersDueToNotExistingCreator()
    {
        EventDto eventWithWrongCreatorDto = EventDto.toStore(
                "Meeting",
                "Planning project",
                ZonedDateTime.now().plusHours(1),
                ZonedDateTime.now().plusHours(2),
                "HIGH",
                calendar.getId(),
                -1L
        );

        ReminderDto reminderDto = ReminderDto.toStoreWithEvent(ZonedDateTime.now().plusMinutes(30));

        assertThrows(NoSuchElementException.class,
                () -> service.createEventWithReminder(eventWithWrongCreatorDto, reminderDto),
                "Must be thrown exception");

        assertEquals(0, eventRepository.count(), "Event must not be not saved due to rollback");
        assertEquals(0, reminderRepository.count(), "Reminders must not be not saved due to rollback");
    }
}
