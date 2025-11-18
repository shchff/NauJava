package ru.grigorii.NauJava.service;

/**
 * Сервис отчётов
 */
public interface ReportService
{
    String getReportContent(Long reportId);
    Long createAndStartBuildingReport();
}
