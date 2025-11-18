package ru.grigorii.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.dto.ReminderDto;
import ru.grigorii.NauJava.repository.ReminderRepository;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Реализация сервиса напоминаний
 */
@Service
public class ReminderServiceImpl implements ReminderService
{
    private final ReminderRepository repository;

    @Autowired
    public ReminderServiceImpl(ReminderRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public List<ReminderDto> findAllBetweenUnsent(ZonedDateTime from, ZonedDateTime to)
    {
        return repository.findByRemindAtBetweenAndSentIsFalse(from, to)
                .stream()
                .map(ReminderDto::fromEntity)
                .toList();
    }
}
