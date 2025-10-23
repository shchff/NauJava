package ru.grigorii.NauJava.repository.custom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.grigorii.NauJava.entity.Reminder;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Реализация кастомного репозитория для календарей через Criteria API.
 */
@Repository
public class ReminderRepositoryCriteria implements ReminderRepositoryCustom
{
    private final EntityManager entityManager;

    @Autowired
    public ReminderRepositoryCriteria(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

    @Override
    public List<Reminder> findByRemindAtBetweenAndSentIsFalse(ZonedDateTime from, ZonedDateTime to)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Reminder> cq = cb.createQuery(Reminder.class);

        Root<Reminder> reminder = cq.from(Reminder.class);

        Predicate remindAtBetween = cb.between(reminder.get("remindAt"), from, to);
        Predicate sentIsFalse = cb.isFalse(reminder.get("sent"));

        cq.select(reminder)
                .where(cb.and(remindAtBetween, sentIsFalse))
                .orderBy(cb.asc(reminder.get("remindAt")));

        return entityManager.createQuery(cq).getResultList();
    }
}
