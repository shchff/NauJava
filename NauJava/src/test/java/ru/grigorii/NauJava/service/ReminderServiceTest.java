package ru.grigorii.NauJava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.grigorii.NauJava.dto.ReminderDto;
import ru.grigorii.NauJava.entity.*;
import ru.grigorii.NauJava.repository.ReminderRepository;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ReminderService test")
@ExtendWith(MockitoExtension.class)
public class ReminderServiceTest
{
    @Mock
    private ReminderRepository repository;

    @InjectMocks
    private ReminderServiceImpl service;

    private ZonedDateTime from;
    private ZonedDateTime to;

    @BeforeEach
    void setUp()
    {
        from = ZonedDateTime.now().minusDays(1);
        to = ZonedDateTime.now().plusDays(1);
    }

    private Reminder buildReminder(
            Long id,
            Long eventId,
            Long userId,
            ZonedDateTime remindAt,
            ReminderChannel channel
    ) {
        Event event = new Event();
        event.setId(eventId);

        User user = new User();
        user.setId(userId);

        Reminder reminder = new Reminder();
        reminder.setId(id);
        reminder.setEvent(event);
        reminder.setUser(user);
        reminder.setRemindAt(remindAt);
        reminder.setSent(false);
        reminder.setChannel(channel);

        return reminder;
    }

    @Test
    @DisplayName("Should return reminder dto's list when repository returns entities")
    void shouldReturnReminderDtoList()
    {
        List<Reminder> listToReturn = List.of(
                buildReminder(1L, 101L, 1001L, from.plusHours(1), ReminderChannel.PUSH),
                buildReminder(2L, 102L, 1002L, from.plusHours(2), ReminderChannel.EMAIL)
        );

        when(repository.findByRemindAtBetweenAndSentIsFalse(from, to))
                .thenReturn(listToReturn);

        List<ReminderDto> result = service.findAllBetweenUnsent(from, to);

        assertEquals(2, result.size());

        ReminderDto dto1 = result.get(0);
        assertEquals(1L, dto1.id());
        assertEquals(101L, dto1.eventId());
        assertEquals(1001L, dto1.userId());
        assertEquals("PUSH", dto1.channel());

        ReminderDto dto2 = result.get(1);
        assertEquals(2L, dto2.id());
        assertEquals(102L, dto2.eventId());
        assertEquals(1002L, dto2.userId());
        assertEquals("EMAIL", dto2.channel());

        verify(repository).findByRemindAtBetweenAndSentIsFalse(from, to);
    }

    @Test
    @DisplayName("Should return an empty list when repository returns no reminders")
    void shouldReturnEmptyList()
    {
        when(repository.findByRemindAtBetweenAndSentIsFalse(from, to))
                .thenReturn(List.of());

        List<ReminderDto> result = service.findAllBetweenUnsent(from, to);

        assertTrue(result.isEmpty());
        verify(repository).findByRemindAtBetweenAndSentIsFalse(from, to);
    }

    @Test
    @DisplayName("Should catch exception when repository throws an error")
    void shouldCatchException()
    {
        when(repository.findByRemindAtBetweenAndSentIsFalse(from, to))
                .thenThrow(new RuntimeException("DB error"));

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> service.findAllBetweenUnsent(from, to)
        );

        assertEquals("DB error", ex.getMessage());
        verify(repository).findByRemindAtBetweenAndSentIsFalse(from, to);
    }
}
