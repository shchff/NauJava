package ru.grigorii.NauJava.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.grigorii.NauJava.repository.EventRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Тест контроллера view событий
 */
@WebMvcTest(EventViewController.class)
@DisplayName("EventViewController test")
public class EventViewControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventRepository repository;

    @Test
    @DisplayName("Operation is successful, return 200")
    void shouldReturnOk200() throws Exception
    {
        mockMvc.perform(get("/events-view")
                        .param("calendarId", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("eventsByCalendar"))
                .andExpect(model().attributeExists("events"))
                .andExpect(model().attributeExists("calendarId"));
    }

    @Test
    @DisplayName("Operation is failure due to invalid calendarId, return 400")
    void shouldReturn400DueToInvalidCalendarId() throws Exception
    {
        mockMvc.perform(get("/events-view")
                        .param("calendarId", "abc"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Operation is failure, return 500")
    void shouldReturn500DueToRepositoryException() throws Exception
    {
        Mockito.when(repository.findAllByCalendarId(1L))
                .thenThrow(new RuntimeException("DB Error"));

        mockMvc.perform(get("/events-view")
                        .param("calendarId", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Unknown server error"));
    }
}
