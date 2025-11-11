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

        Mockito.when(reportRepository.findById(1L))
                .thenReturn(Optional.of(report));

        reportService.buildReportAsync(1L);

        String result = reportService.getReportContent(1L);

        assertEquals("Отчёт ещё формируется", result);
    }

    @DisplayName("Report has error")
    @Test
    void shouldReturnReportHasError()
    {
        Report report = new Report();
        report.setId(1L);
        report.setStatus(ReportStatus.CREATED);

        Mockito.when(reportRepository.findById(1L))
                .thenReturn(Optional.of(report));

        Mockito.when(reportRepository.save(Mockito.any()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Mockito.when(userRepository.count())
                .thenThrow(new RuntimeException("DB error"));

        reportService.buildReportAsync(1L).join();

        String result = reportService.getReportContent(1L);

        assertEquals("Произошла ошибка при формировании отчёта", result);
    }
}
