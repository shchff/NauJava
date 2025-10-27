package ru.grigorii.NauJava.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.grigorii.NauJava.controller.exception.ApiError;
import ru.grigorii.NauJava.dto.ReminderDto;
import ru.grigorii.NauJava.service.ReminderService;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Контроллер напоминаний
 */
@RestController
@RequestMapping("/reminders")
@Tag(name = "Напоминания", description = "Кастомные методы для напоминаний")
public class ReminderController
{
    private final ReminderService service;

    @Autowired
    public ReminderController(ReminderService service)
    {
        this.service = service;
    }

    @GetMapping("/search-unsent")
    @Operation(
            summary = "Получить все неотправленные напоминания в диапазоне дат",
            description = "Возвращает напоминания, у которых время отправки находится в указанном диапазоне и они ещё не отправлены"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Напоминания найдены",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = ReminderDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный формат from и/или to",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Внутренняя ошибка",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class)
                            )
                    )
            }
    )
    public List<ReminderDto> getUnsentRemindersBetween(
            @Parameter(
                    description = "Начало временного диапазона",
                    example = "2025-10-26T18:00:00+05:00",
                    required = true,
                    schema = @Schema(type = "string", format = "date-time")
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime from,

            @Parameter(
                    description = "Окончание временного диапазона",
                    example = "2025-10-27T10:00:00+05:00",
                    required = true,
                    schema = @Schema(type = "string", format = "date-time")
            )
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) ZonedDateTime to
    )
    {
        return service.findAllBetweenUnsent(from, to);
    }
}
