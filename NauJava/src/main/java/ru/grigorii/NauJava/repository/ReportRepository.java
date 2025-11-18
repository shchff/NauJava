package ru.grigorii.NauJava.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.grigorii.NauJava.entity.Report;

/**
 * Репозиторий отчётов
 */
public interface ReportRepository extends JpaRepository<Report, Long>
{
}
