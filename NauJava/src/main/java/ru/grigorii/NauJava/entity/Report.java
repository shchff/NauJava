package ru.grigorii.NauJava.entity;

import jakarta.persistence.*;

/**
 * Сущность отчёт
 */
@Entity
@Table(name = "reports")
public class Report
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Статус отчёта
     */
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReportStatus status;

    /**
     * Содержимое отчёта
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public ReportStatus getStatus()
    {
        return status;
    }

    public void setStatus(ReportStatus status)
    {
        this.status = status;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
