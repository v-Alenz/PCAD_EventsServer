package com.events.api.eventsapirest.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.events.api.eventsapirest.Repository.Entity.Event;


public interface EventRepo extends JpaRepository<Event, String>{
    
}
