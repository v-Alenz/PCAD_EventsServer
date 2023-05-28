package com.events.api.eventsapirest.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.events.api.eventsapirest.Repository.EventRepo;
import com.events.api.eventsapirest.Repository.Entity.Event;


@Service
public class EventService {
    
    @Autowired
    private EventRepo eventRepo;


    public Event getEvent(String name){
        var event = eventRepo.findById(name);
        return (event.isPresent())?event.get():null;
    }

    public List<Event> getAllEvents() {
        return eventRepo.findAll();
    }

}
