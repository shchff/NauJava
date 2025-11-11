package ru.grigorii.NauJava.service;

import java.util.concurrent.CompletableFuture;

/**
 * Сервис отчётов
 */
public interface ReportService
{
    String getReportContent(Long reportId);
    Long createReport();
    CompletableFuture<Void> buildReportAsync(Long id);
}
