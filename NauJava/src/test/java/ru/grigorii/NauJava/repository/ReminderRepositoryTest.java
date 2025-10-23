package ru.grigorii.NauJava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.grigorii.NauJava.entity.Calendar;
import ru.grigorii.NauJava.entity.Event;
import ru.grigorii.NauJava.entity.Reminder;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.custom.ReminderRepositoryCriteria;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Тесты для обеих (Spring Data JPA и Criteria API) реализаций репозиториев напоминаний.
 * Тестируются только методы, реализованные через написание кода.
 * Используется in-memory СУБД H2.
 */
@DisplayName("ReminderRepository test")
@DataJpaTest
@Import(ReminderRepositoryCriteria.class)
public class ReminderRepositoryTest
{
    @Autowired
    private ReminderRepository springDataRepository;

    @Autowired
    private ReminderRepositoryCriteria criteriaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private EventRepository eventRepository;

    private User user;
    private Event event;

    @BeforeEach
    void setUp()
    {
        springDataRepository.deleteAll();
        userRepository.deleteAll();
        calendarRepository.deleteAll();
        eventRepository.deleteAll();

        user = userRepository.save(
                User.requiredFields(
                        "grisaserbakov0@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );

        Calendar calendar = calendarRepository.save(
                Calendar.requiredFields("Work", user)
        );

        event = eventRepository.save(
                Event.requiredFields(
                        "Meeting",
                        ZonedDateTime.now().plusHours(3),
                        calendar,
                        user
                )
        );
    }

    @DisplayName("Should return one unsent reminder in time range")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldReturnOneUnsentReminderInRange(RepositoryType repositoryType)
    {
        ZonedDateTime now = ZonedDateTime.now();

        springDataRepository.save(
                Reminder.requiredFields(event, user, now.plusMinutes(30))
        );

        List<Reminder> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
            case CRITERIA_API -> criteriaRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
        };

        assertEquals(1, result.size(), "Exactly one reminder must be found");
        assertEquals(user.getId(), result.getFirst().getUser().getId(),
                "Resulted user id must be equal to the origin value of user id");
        assertFalse(result.getFirst().getSent(), "Reminder must not be sent");
    }

    @DisplayName("Should not return any reminder in time range")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldNotReturnReminderOutsideRange(RepositoryType repositoryType)
    {
        ZonedDateTime now = ZonedDateTime.now();

        springDataRepository.save(
                Reminder.requiredFields(event, user, now.plusHours(3))
        );

        List<Reminder> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
            case CRITERIA_API -> criteriaRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
        };

        assertTrue(result.isEmpty(), "Reminder outside range must not be found");
    }

    @DisplayName("Should not return any sent reminder")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldNotReturnSentReminders(RepositoryType repositoryType)
    {
        ZonedDateTime now = ZonedDateTime.now();

        Reminder reminder = Reminder.requiredFields(event, user, now.plusMinutes(30));
        reminder.setSent(true);
        springDataRepository.save(reminder);

        List<Reminder> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
            case CRITERIA_API -> criteriaRepository.findByRemindAtBetweenAndSentIsFalse(now, now.plusHours(1));
        };

        assertTrue(result.isEmpty(), "Sent reminder must not be found");
    }
}
