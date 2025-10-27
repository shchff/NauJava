package ru.grigorii.NauJava.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.grigorii.NauJava.service.CalendarService;
import ru.grigorii.NauJava.service.exception.UserNotFoundException;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * Тесты контроллера календарей
 */
@WebMvcTest(CalendarController.class)
@DisplayName("CalendarController test")
public class CalendarControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalendarService service;

    @Test
    @DisplayName("Operation is successful, return 200")
    void shouldReturnOk200() throws Exception
    {
        Mockito.when(service.findAllCalendarsByUserId(1L))
                .thenReturn(List.of());

        mockMvc.perform(get("/calendars/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Operation is failure due to invalid userId, return 400")
    void shouldReturn400DueToInvalidUserId() throws Exception
    {
        mockMvc.perform(get("/calendars/user/xyz"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Operation is failure due to user is not found, return 500")
    void shouldReturn500DueToUserNotFound() throws Exception
    {
        Mockito.when(service.findAllCalendarsByUserId(1L))
                .thenThrow(new UserNotFoundException(1L));

        mockMvc.perform(get("/calendars/user/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Service exception: User with id=1 not found"));
    }
}
