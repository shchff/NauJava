package ru.grigorii.NauJava.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.grigorii.NauJava.entity.Report;
import ru.grigorii.NauJava.entity.ReportStatus;
import ru.grigorii.NauJava.repository.CalendarRepository;
import ru.grigorii.NauJava.repository.ReportRepository;
import ru.grigorii.NauJava.repository.UserRepository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@DisplayName("ReportService test")
@ExtendWith(MockitoExtension.class)
public class ReportServiceTest
{
    @Mock
    private ReportRepository reportRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CalendarRepository calendarRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp()
    {
        reportRepository.deleteAll();
    }

    @DisplayName("Report is still creating")
    @Test
    void shouldReturnReportStillCreating()
    {
        Report report = new Report();
        report.setId(1L);
        report.setStatus(ReportStatus.CREATED);
        report.setContent(null);
        Mockito.when(reportRepository.save(any(Report.class)))
                .thenAnswer(invocation -> {
                    Report saved = invocation.getArgument(0);
                    if (saved.getId() == null)
                    {
                        saved.setId(1L);
                    }
                    return saved;
                });

        Long id = reportService.createAndStartBuildingReport();

        Mockito.when(reportRepository.findById(id))
                .thenReturn(Optional.of(report));

        String result = reportService.getReportContent(id);

        assertEquals("Отчёт ещё формируется", result);
    }

    @DisplayName("Report has error during building")
    @Test
    void shouldReturnReportHasError()
    {
        Report report = new Report();
        report.setId(1L);
        report.setStatus(ReportStatus.CREATED);
        report.setContent(null);

        Mockito.when(reportRepository.save(any(Report.class)))
                .thenAnswer(invocation -> {
                    Report saved = invocation.getArgument(0);
                    if (saved.getId() == null)
                    {
                        saved.setId(1L);
                    }
                    return saved;
                });

        Mockito.when(reportRepository.findById(1L))
                .thenReturn(Optional.of(report))
                .thenReturn(Optional.of(new Report() {{
                    setId(1L);
                    setStatus(ReportStatus.ERROR);
                    setContent(null);
                }}));

        Mockito.when(userRepository.count())
                .thenThrow(new RuntimeException("DB error"));

        Long id = reportService.createAndStartBuildingReport();

        try
        {
            Thread.sleep(500);
        }
        catch (InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }

        String result = reportService.getReportContent(id);

        assertEquals("Произошла ошибка при формировании отчёта", result);
    }
}
