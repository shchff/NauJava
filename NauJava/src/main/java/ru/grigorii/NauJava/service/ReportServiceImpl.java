package ru.grigorii.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.grigorii.NauJava.entity.Calendar;
import ru.grigorii.NauJava.entity.Report;
import ru.grigorii.NauJava.entity.ReportStatus;
import ru.grigorii.NauJava.repository.CalendarRepository;
import ru.grigorii.NauJava.repository.ReportRepository;
import ru.grigorii.NauJava.repository.UserRepository;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Реализация сервиса отчётов
 */
@Service
public class ReportServiceImpl implements ReportService
{
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, CalendarRepository calendarRepository)
    {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.calendarRepository = calendarRepository;
    }

    @Override
    public String getReportContent(Long reportId)
    {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Report with id=%d not found", reportId)));

        return switch (report.getStatus())
        {
            case CREATED -> "Отчёт ещё формируется";
            case ERROR -> "Произошла ошибка при формировании отчёта";
            case COMPLETED -> report.getContent();
        };
    }

    @Override
    public Long createReport()
    {
        Report report = new Report();
        report.setStatus(ReportStatus.CREATED);
        report = reportRepository.save(report);
        return report.getId();
    }

    @Override
    public CompletableFuture<Void> buildReportAsync(Long id)
    {
        class Results
        {
            long countUsers;
            long timeCountUsers;

            Iterable<Calendar> calendars;
            long timeFetchCalendars;

            long totalTime;
        }

        Results results = new Results();

        return CompletableFuture.runAsync(() -> {

            Report report = reportRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Report with id=%d not found", id)));

            try
            {
                AtomicReference<Exception> exceptionHolder = new AtomicReference<>(null);

                long totalStart = System.currentTimeMillis();

                Thread countUsersThread = new Thread(() -> {
                    long start = System.currentTimeMillis();

                    try
                    {
                        results.countUsers = userRepository.count();
                    }
                    catch (Exception e)
                    {
                        exceptionHolder.set(e);
                    }

                    results.timeCountUsers = System.currentTimeMillis() - start;
                });

                Thread fetchEntitiesThread = new Thread(() -> {
                    long start = System.currentTimeMillis();

                    try
                    {
                        results.calendars = calendarRepository.findAll();
                    }
                    catch (Exception e)
                    {
                        exceptionHolder.set(e);
                    }

                    results.timeFetchCalendars = System.currentTimeMillis() - start;
                });

                countUsersThread.start();
                fetchEntitiesThread.start();

                countUsersThread.join();
                fetchEntitiesThread.join();

                if (exceptionHolder.get() != null)
                {
                    throw exceptionHolder.get();
                }

                results.totalTime = System.currentTimeMillis() - totalStart;

                StringBuilder html = new StringBuilder();

                html.append("<html><body>");
                html.append("<h1>Отчёт</h1>");

                html.append("<h2>Количество пользователей</h2>");
                html.append("<p>").append(results.countUsers).append("</p>");
                html.append("<p>Время вычисления: ").append(results.timeCountUsers).append(" ms</p>");

                html.append("<h2>Календари</h2>");
                html.append("<table border='1'>");
                html.append("<tr><th>ID</th><th>Название</th></tr>");

                for (Calendar calendar : results.calendars)
                {
                    html.append("<tr>");
                    html.append("<td>").append(calendar.getId()).append("</td>");
                    html.append("<td>").append(calendar.getName()).append("</td>");
                    html.append("</tr>");
                }

                html.append("</table>");
                html.append("<p>Время вычисления: ").append(results.timeFetchCalendars).append(" ms</p>");

                html.append("<h2>Общее время формирования</h2>");
                html.append("<p>").append(results.totalTime).append(" ms</p>");

                html.append("</body></html>");

                report.setContent(html.toString());
                report.setStatus(ReportStatus.COMPLETED);
                reportRepository.save(report);

            }
            catch (Exception e)
            {
                report.setStatus(ReportStatus.ERROR);
                report.setContent(null);
                reportRepository.save(report);
            }
        });
    }
}
