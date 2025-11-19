package ru.grigorii.NauJava.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.grigorii.NauJava.dto.CalendarDto;
import ru.grigorii.NauJava.entity.Calendar;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.CalendarRepository;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.service.exception.UserNotFoundException;
import java.time.ZonedDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalendarService test")
class CalendarServiceTest
{
    @Mock
    private CalendarRepository calendarRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CalendarServiceImpl service;

    private Calendar buildCalendar(Long id, Long ownerId, String name)
    {
        User owner = new User();
        owner.setId(ownerId);

        Calendar calendar = new Calendar();
        calendar.setId(id);
        calendar.setName(name);
        calendar.setDescription("desc-" + id);
        calendar.setShared(false);
        calendar.setOwner(owner);
        calendar.setCreatedAt(ZonedDateTime.now());

        return calendar;
    }

    @Test
    @DisplayName("Should return calendar dto list when user exists and repository returns calendars")
    void shouldReturnCalendarDtoList()
    {
        Long userId = 10L;

        when(userRepository.existsById(userId)).thenReturn(true);

        List<Calendar> calendarsToReturn = List.of(
                buildCalendar(1L, userId, "Work"),
                buildCalendar(2L, userId, "Personal")
        );

        when(calendarRepository.findAllByUserId(userId))
                .thenReturn(calendarsToReturn);

        List<CalendarDto> result = service.findAllCalendarsByUserId(userId);

        assertEquals(2, result.size());

        CalendarDto dto1 = result.get(0);
        assertEquals(1L, dto1.id());
        assertEquals("Work", dto1.name());
        assertEquals("desc-1", dto1.description());
        assertFalse(dto1.shared());
        assertEquals(userId, dto1.ownerId());

        CalendarDto dto2 = result.get(1);
        assertEquals(2L, dto2.id());
        assertEquals("Personal", dto2.name());
        assertEquals("desc-2", dto2.description());
        assertEquals(userId, dto2.ownerId());

        verify(userRepository).existsById(userId);
        verify(calendarRepository).findAllByUserId(userId);
    }

    @Test
    @DisplayName("Should return empty list when user exists but no calendars found")
    void shouldReturnEmptyList()
    {
        Long userId = 10L;

        when(userRepository.existsById(userId)).thenReturn(true);
        when(calendarRepository.findAllByUserId(userId)).thenReturn(List.of());

        List<CalendarDto> result = service.findAllCalendarsByUserId(userId);

        assertTrue(result.isEmpty());
        verify(userRepository).existsById(userId);
        verify(calendarRepository).findAllByUserId(userId);
    }

    @Test
    @DisplayName("Should throw exception when user does not exist")
    void shouldThrowWhenUserDoesNotExist()
    {
        Long userId = 10L;

        when(userRepository.existsById(userId)).thenReturn(false);

        UserNotFoundException ex = assertThrows(
                UserNotFoundException.class,
                () -> service.findAllCalendarsByUserId(userId)
        );

        assertEquals("User with id=10 not found", ex.getMessage());
        verify(userRepository).existsById(userId);
    }
}
