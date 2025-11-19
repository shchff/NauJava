package ru.grigorii.NauJava.rest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import ru.grigorii.NauJava.entity.Calendar;
import ru.grigorii.NauJava.entity.Event;
import ru.grigorii.NauJava.entity.Reminder;
import ru.grigorii.NauJava.entity.User;
import ru.grigorii.NauJava.repository.CalendarRepository;
import ru.grigorii.NauJava.repository.EventRepository;
import ru.grigorii.NauJava.repository.ReminderRepository;
import ru.grigorii.NauJava.repository.UserRepository;
import java.time.ZonedDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DisplayName("Reminder API test")
public class ReminderApiTest
{
    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ReminderRepository reminderRepository;

    @Autowired
    private CalendarRepository calendarRepository;

    @BeforeEach
    void setUp()
    {
        reminderRepository.deleteAll();
        eventRepository.deleteAll();
        calendarRepository.deleteAll();
        userRepository.deleteAll();

        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        User user = userRepository.save(
                User.requiredFields(
                        "grisaserbakov0@gmail.com",
                        "password",
                        "Asia/Yekaterinburg"
                )
        );

        Calendar calendar = calendarRepository.save(
                Calendar.requiredFields("Calendar", user)
        );

        Event event = eventRepository.save(
                Event.requiredFields(
                        "Event",
                        ZonedDateTime.parse("2025-11-02T12:00:00+05:00"),
                        calendar,
                        user)
        );

        reminderRepository.save(
                Reminder.requiredFields(event, user,
                        ZonedDateTime.parse("2025-10-26T18:30:00+05:00"))
        );

        reminderRepository.save(
                Reminder.requiredFields(event, user,
                        ZonedDateTime.parse("2025-10-26T21:00:00+05:00"))
        );

        Reminder sent = Reminder.requiredFields(event, user,
                ZonedDateTime.parse("2025-10-26T19:00:00+05:00"));
        sent.setSent(true);

        reminderRepository.save(sent);
    }

    @Test
    @DisplayName("Should successfully return reminders")
    void getUnsentRemindersBetween_ok()
    {

        given()
                .queryParam("from", "2025-10-26T18:00:00+05:00")
                .queryParam("to", "2025-10-27T00:00:00+05:00")
                .when()
                .get("/reminders/search-unsent")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("[0].sent", is(false));
    }

    @Test
    @DisplayName("Should return error with status code 400")
    void getUnsentRemindersBetween_invalidDate()
    {
        given()
                .queryParam("from", "bad-date")
                .queryParam("to", "2025-10-27T00:00:00+05:00")
                .when()
                .get("/reminders/search-unsent")
                .then()
                .statusCode(400)
                .body("error", equalTo("Bad Request"));
    }
}
