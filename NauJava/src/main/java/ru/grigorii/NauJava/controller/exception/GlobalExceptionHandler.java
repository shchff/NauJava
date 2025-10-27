package ru.grigorii.NauJava.controller.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.grigorii.NauJava.service.exception.ServiceException;
import java.time.ZonedDateTime;

/**
 * Глобальный обработчик ошибок контроллеров
 */
@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException e)
    {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException e)
    {
        return buildResponse(HttpStatus.BAD_REQUEST,
                "Invalid path parameter: " + e.getName());
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiError> handleMissingPathVariable(MissingPathVariableException e)
    {
        return buildResponse(HttpStatus.BAD_REQUEST,
                "Missing required path variable: " + e.getVariableName());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<ApiError> handleNotFound(EmptyResultDataAccessException e)
    {
        return buildResponse(HttpStatus.NOT_FOUND,
                "Requested resource not found");
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(Exception e)
    {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ApiError> handleServiceException(ServiceException e)
    {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Service exception: " + e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleUnknownError(Exception e)
    {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Unknown server error");
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message)
    {

        ApiError apiError = new ApiError(
                ZonedDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message
        );

        return ResponseEntity.status(status).body(apiError);
    }
}
