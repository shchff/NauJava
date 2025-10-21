package ru.grigorii.NauJava.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.Calendar;
import ru.grigorii.NauJava.entity.CalendarMembership;
import ru.grigorii.NauJava.entity.User;
import java.util.List;

/**
 * Реализация кастомного репозитория для календарей через Criteria API.
 */
@Repository
public class CalendarRepositoryCriteria implements CalendarRepositoryCustom
{
    private final EntityManager entityManager;

    @Autowired
    public CalendarRepositoryCriteria(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public List<Calendar> findAllByUserId(Long userId)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Calendar> cq = cb.createQuery(Calendar.class);

        Root<Calendar> calendar = cq.from(Calendar.class);
        Join<Calendar, CalendarMembership> membership = calendar.join("members", JoinType.INNER);
        Join<CalendarMembership, User> user = membership.join("member", JoinType.INNER);

        Predicate predicate = cb.equal(user.get("id"), userId);
        cq.select(calendar).where(predicate).distinct(true);

        return entityManager.createQuery(cq).getResultList();
    }
}
