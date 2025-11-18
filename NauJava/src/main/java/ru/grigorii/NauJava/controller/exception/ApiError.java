package ru.grigorii.NauJava.controller.exception;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.ZonedDateTime;

/**
 * Ошибка API
 */
@Schema(description = "Ошибка API")
public record ApiError(@Schema(description = "Время возникновения ошибки") ZonedDateTime timestamp,
                       @Schema(description = "HTTP статус") int status,
                       @Schema(description = "Наименование ошибки") String error,
                       @Schema(description = "Сообщение ошибки") String message)
{
}
