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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.grigorii.NauJava.controller.exception.ApiError;
import ru.grigorii.NauJava.dto.CalendarDto;
import ru.grigorii.NauJava.service.CalendarService;
import java.util.List;

/**
 * Контроллер календарей
 */
@RestController
@RequestMapping("/api/calendars")
@Tag(name = "Календари", description = "Кастомные методы для календаря")
public class CalendarController
{
    private final CalendarService service;

    @Autowired
    public CalendarController(CalendarService service)
    {
        this.service = service;
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Получить все календари, в которых состоит пользователь"
    )
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Календари найдены",
                        content = @Content(
                                mediaType = "application/json",
                                array = @ArraySchema(schema = @Schema(implementation = CalendarDto.class))
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Некорректный формат ID",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ApiError.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "500",
                        description = "Пользователь с указанным ID не существует или иная внутренняя ошибка",
                        content = @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = ApiError.class)
                        )
                )
        }
    )
    public List<CalendarDto> getUserCalendars(
            @Parameter(
                    description = "ID пользователя",
                    required = true
            )
            @PathVariable Long userId
    )
    {
        return service.findAllCalendarsByUserId(userId);
    }
}
