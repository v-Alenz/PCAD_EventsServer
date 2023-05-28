package com.events.api.eventsapirest.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.events.api.eventsapirest.Repository.Entity.Event;
import com.events.api.eventsapirest.Service.EventService;


@RestController
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/event")
    public Event getEvent(@RequestParam String name) {
        return eventService.getEvent(name);
    }

    @GetMapping("/events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

}
