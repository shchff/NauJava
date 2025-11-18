package ru.grigorii.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.dto.CalendarDto;
import ru.grigorii.NauJava.repository.CalendarRepository;
import ru.grigorii.NauJava.repository.UserRepository;
import ru.grigorii.NauJava.service.exception.UserNotFoundException;
import java.util.List;

/**
 * Реализация сервиса календарей
 */
@Service
public class CalendarServiceImpl implements CalendarService
{
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    @Autowired
    public CalendarServiceImpl(CalendarRepository calendarRepository, UserRepository userRepository)
    {
        this.calendarRepository = calendarRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CalendarDto> findAllCalendarsByUserId(Long userId)
    {
        if (!userRepository.existsById(userId))
        {
            throw new UserNotFoundException(userId);
        }

        return calendarRepository.findAllByUserId(userId)
                .stream()
                .map(CalendarDto::fromEntity)
                .toList();
    }
}
