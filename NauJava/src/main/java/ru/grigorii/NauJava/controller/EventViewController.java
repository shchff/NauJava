package ru.grigorii.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.grigorii.NauJava.dto.EventDto;
import ru.grigorii.NauJava.repository.EventRepository;
import java.util.List;

/**
 * Контроллер view событий
 */
@Controller
public class EventViewController
{
    private final EventRepository repository;

    @Autowired
    public EventViewController(EventRepository repository)
    {
        this.repository = repository;
    }

//    @GetMapping("/events-view")
//    public String eventsByCalendarView(@RequestParam Long calendarId, Model model)
//    {
//        List<EventDto> events = repository.findAllByCalendarId(calendarId)
//                .stream()
//                .map(EventDto::fromEntity)
//                .toList();
//
//        model.addAttribute("events", events);
//        model.addAttribute("calendarId", calendarId);
//        return "eventsByCalendar";
//    }
}
