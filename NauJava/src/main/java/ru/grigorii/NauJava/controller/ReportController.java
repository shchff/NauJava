package ru.grigorii.NauJava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.grigorii.NauJava.service.ReportService;

@RestController
@RequestMapping("/reports")
@Tag(name = "Отчёты", description = "Методы для создания и получения отчётов")
public class ReportController
{
    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService)
    {
        this.reportService = reportService;
    }

    @PostMapping
    @Operation(
            summary = "Создать отчёт",
            description = "Создаёт отчёт и начинает его формирование"
    )
    public Long createReport()
    {
        return reportService.createAndStartBuildingReport();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получить отчёт",
            description = "Возвращает содержимое отчёта в формате HTML, или статус: ошибка формирования или отчёт создан, но не готов"
    )
    public String getReport(@PathVariable Long id)
    {
        return reportService.getReportContent(id);
    }
}
