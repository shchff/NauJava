package ru.grigorii.NauJava.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.grigorii.NauJava.service.ReminderService;
import java.time.ZonedDateTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Тесты контроллера напоминаний
 */
@WebMvcTest(ReminderController.class)
@DisplayName("ReminderController test")
public class ReminderControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReminderService service;

    @Test
    @DisplayName("Operation is successful, return 200")
    void shouldReturnOk200() throws Exception
    {
        ZonedDateTime from = ZonedDateTime.parse("2025-10-26T18:00:00+05:00");
        ZonedDateTime to = ZonedDateTime.parse("2025-10-27T10:00:00+05:00");

        Mockito.when(service.findAllBetweenUnsent(from, to))
                .thenReturn(List.of());

        mockMvc.perform(get("/reminders/search-unsent")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Operation is failure, return 400")
    void shouldReturn400DueToInvalidDateParameter() throws Exception {
        mockMvc.perform(get("/reminders/search-unsent")
                        .param("from", "INVALID")
                        .param("to", "INVALID"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Operation is failure, return 500")
    void shouldReturn500() throws Exception {
        ZonedDateTime from = ZonedDateTime.now();
        ZonedDateTime to = from.plusDays(1);

        Mockito.when(service.findAllBetweenUnsent(from, to))
                .thenThrow(new RuntimeException("Service failure"));

        mockMvc.perform(get("/reminders/search-unsent")
                        .param("from", from.toString())
                        .param("to", to.toString()))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unknown server error"));
    }
}
