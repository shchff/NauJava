package ru.grigorii.NauJava.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.grigorii.NauJava.entity.*;
import ru.grigorii.NauJava.repository.custom.CalendarRepositoryCriteria;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты для обеих (Spring Data JPA и Criteria API) реализаций репозиториев календарей.
 * Тестируются только методы, реализованные через написание кода.
 * Используется in-memory СУБД H2.
 */
@DisplayName("CalendarRepository test")
@Import(CalendarRepositoryCriteria.class)
@DataJpaTest
public class CalendarRepositoryTest
{
    @Autowired
    private CalendarRepository springDataRepository;

    @Autowired
    private CalendarRepositoryCriteria criteriaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CalendarMembershipRepository membershipRepository;

    private User user;
    private Calendar calendar2;

    @BeforeEach
    void setUp()
    {

        springDataRepository.deleteAll();
        userRepository.deleteAll();
        membershipRepository.deleteAll();

        user = userRepository.save(
                User.requiredFields(
                        "grisaserbakov0@gmail.com",
                        "123",
                        "Asia/Yekaterinburg"
                )
        );

        Calendar calendar1 = springDataRepository.save(
                Calendar.requiredFields("Work", user)
        );

        calendar2 = springDataRepository.save(
                Calendar.requiredFields("Personal", user)
        );

        membershipRepository.save(
                CalendarMembership.requiredFields(
                        calendar1,
                        user,
                        CalendarRole.OWNER
                )
        );
    }

    @DisplayName("Should return exactly one calendar for user")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldReturnOneCalendarForUser(RepositoryType repositoryType)
    {
        List<Calendar> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findAllByUserId(user.getId());
            case CRITERIA_API -> criteriaRepository.findAllByUserId(user.getId());
        };

        assertEquals(1, result.size(), "User must be a member of exactly one calendar");

        Calendar found = result.getFirst();
        assertEquals("Work", found.getName(), "Name of found calendar must be 'Work'");
    }

    @DisplayName("Should return zero calendars for not existing user")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldReturnZeroCalendars(RepositoryType repositoryType)
    {
        List<Calendar> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findAllByUserId(-1L);
            case CRITERIA_API -> criteriaRepository.findAllByUserId(-1L);
        };

        assertTrue(result.isEmpty(), "Not existing user must not have any calendar");
    }

    @DisplayName("Should find two calendars where user is a member")
    @ParameterizedTest(name = "Repository type: {0}")
    @EnumSource(RepositoryType.class)
    void shouldReturnTwoCalendars(RepositoryType repositoryType)
    {
        membershipRepository.save(
                CalendarMembership.requiredFields(
                        calendar2,
                        user,
                        CalendarRole.OWNER
                )
        );

        List<Calendar> result = switch (repositoryType)
        {
            case SPRING_DATA -> springDataRepository.findAllByUserId(user.getId());
            case CRITERIA_API -> criteriaRepository.findAllByUserId(user.getId());
        };

        assertEquals(2, result.size(), "User must be a member of two calendars");

        boolean hasWork = result.stream().anyMatch(c -> "Work".equals(c.getName()));
        boolean hasPersonal = result.stream().anyMatch(c -> "Personal".equals(c.getName()));

        assertTrue(hasWork, "There must be a calendar named `Work`");
        assertTrue(hasPersonal, "There must be a calendar named `Personal`");
    }
}
