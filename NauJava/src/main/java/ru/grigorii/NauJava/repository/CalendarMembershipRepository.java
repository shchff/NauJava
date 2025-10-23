package ru.grigorii.NauJava.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.CalendarMembership;
import ru.grigorii.NauJava.entity.CalendarMembershipKey;
import java.util.List;

/**
 * Репозиторий для работы с сущностью участие в календаре
 */
@Repository
public interface CalendarMembershipRepository extends CrudRepository<CalendarMembership, CalendarMembershipKey>
{
    List<CalendarMembership> findAllByCalendarId(Long id);
}
