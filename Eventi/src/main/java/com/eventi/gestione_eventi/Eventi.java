package com.eventi.gestione_eventi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


import com.eventi.calvino.Subscriber;
import com.eventi.messaggi.AddSeatsEventMessage;
import com.eventi.messaggi.BookSeatsEventMessage;
import com.eventi.messaggi.BroadcastEventsListMesage;
import com.eventi.messaggi.CloseEventMessage;
import com.eventi.messaggi.CreateEventMessage;
import com.eventi.messaggi.ErrorResponse;
import com.eventi.messaggi.EventMessage;
import com.eventi.messaggi.OkResponse;


public class Eventi extends Subscriber implements Runnable {    

    private Map<String,Evento> eventList;
    private Map<String, LinkedList<BookSeatsEventMessage>> pendigRequestsQueue;
    private EventiMySql sql;


    public Eventi() {
        sql = new EventiMySql();
        pendigRequestsQueue = new HashMap<>();
        eventList = sql.eventGetList();
        if(eventList == null){
            System.out.println("EVENTS: Unable to contact the Database, Shutting down");
            System.exit(1);
        }
    }

    public void crea(CreateEventMessage eventMessage) {
        String name = eventMessage.getEventName();
        Integer seats = eventMessage.getEventSeats(); 
        if (this.containsEvent(name) 
        || name.contains("?")
        || name.contains(":")
        || name.contains(",")
        || name.contains(".")
        || name.contains("!")
        || name.contains("=")
        || name.contains("\"")) // Avremmo potuto usare il patterna matching ma perche farlo
            sendResponse(false, eventMessage.getClientId());
        try {
            eventList.put(name,new Evento(name, seats));
            sql.eventCreate(name, seats);
        } catch (Exception e) {
            sendResponse(false, eventMessage.getClientId());
        }
        updateEventTopic();
        sendResponse(true, eventMessage.getClientId());
    }

    public void aggiungi(AddSeatsEventMessage eventMessage) {
        String name = eventMessage.getEventName();
        Integer seats = eventMessage.getEventSeats(); 
        if(!this.containsEvent(name))
            sendResponse(false, eventMessage.getClientId());
        try {
            eventList.get(name).addSeats(seats);
            sql.eventAdd(name, seats);
        } catch (Exception e) {
            sendResponse(false, eventMessage.getClientId());
        }
        if(pendigRequestsQueue.containsKey(name)){
            var aux = pendigRequestsQueue.get(name).element();
            if(eventList.get(name).getSeats() >= aux.getEventSeats()){
                prenota(aux);
            }
        }
        updateEventTopic();
        sendResponse(true, eventMessage.getClientId());
    }

    public void prenota(BookSeatsEventMessage eventMessage) {
        String name = eventMessage.getEventName();
        Integer seats = eventMessage.getEventSeats();
        if(!this.containsEvent(name))
            sendResponse(false, eventMessage.getClientId());
        try {
            eventList.get(name).removeSeats(seats);
            sql.eventBook(name, seats);
        } catch (ArgumentOutOfBoundException e) {
            if (!pendigRequestsQueue.containsKey(name)) {
                pendigRequestsQueue.put(name, new LinkedList<>());
            }
            pendigRequestsQueue.get(name).add(eventMessage);
            return;
        } catch (Exception e) {
            sendResponse(false, eventMessage.getClientId());
        }
        updateEventTopic();
        sendResponse(true, eventMessage.getClientId());

    }

    /* DEBUG */ public void listaEventi() {
        for (Evento evento : eventList.values()) {
            System.out.println( evento.toString()); 
        }
    }

    public void chiudi(CloseEventMessage eventMessage) { 
        String name = eventMessage.getEventName();
        if(!this.containsEvent(name))
            sendResponse(false, eventMessage.getClientId());
        try {
            eventList.remove(name);       
            sql.eventeDelete(name);
        } catch (Exception e) {
            sendResponse(false, eventMessage.getClientId());
        }
        if(pendigRequestsQueue.containsKey(name)){
            for (var messages : pendigRequestsQueue.get(name)) {
                sendResponse(false, messages.getClientId());
            }
            pendigRequestsQueue.remove(name);
        }
        updateEventTopic();
        sendResponse(true, eventMessage.getClientId());
    }

    public void sendResponse(boolean ok, Integer id){
        EventMessage response;
        if(ok){
            response = new OkResponse();
        } else {
            response = new ErrorResponse();
        }
        try {
            SubscribeProd("topic-"+id.toString());
            produce("topic-"+id.toString(), response);
            synchronized(getMyProducer().get("topic-"+id.toString())){
                getMyProducer().get("topic-"+id.toString()).notifyAll();
            }
            UnSubscribeProd("topic-"+id.toString());
        } catch (Exception e) {
            System.out.println("EVENTS: Fatal error while sending messsages: " + e.getMessage());
            System.exit(1);
        }
    }

    private boolean containsEvent(String name) {
        return this.eventList.containsKey(name);
    }

    private void initializeTopics(){
        try {
            SubscribeCons("topicEventMessages");   //Topic da cui riceveremo i messaggi dai server thread
            SubscribeProd("topicEventsBroadcast"); //Topic sulla quale facciamo il broadcast della lista eventi
            SubscribeCons("topicEventsBroadcast"); //Sottoscrivo sia come producer che consumer per consentire solo peeker su questo topic
            produce("topicEventsBroadcast", new BroadcastEventsListMesage(eventList));
            SubscribeProd("topicFatalError");      //Topic su cui comunico al main che il thread non e' piu in grado di continuare l'esecuzione
        } catch (Exception e) {
            System.out.println("unable tu initialize topics: " + e.getMessage());
            System.exit(1);
        }
    }

    private void updateEventTopic(){
        try {
            eventList = sql.eventGetList(); // Prima di modificare il topic in broadcast mi sincronizzo con il database
            produce("topicEventsBroadcast", new BroadcastEventsListMesage(eventList));
            consume("topicEventsBroadcast");
        } catch (Exception e) { 
            System.out.println("EVENTS: Fatal error syncronizing with the Database. Shuting down");
            System.exit(1);
        }
    }

    @Override
    public void run() {
        System.out.println("EVENTS: Starting event hadler thread");
        initializeTopics();
        System.out.println("EVENTS: Event start polling on topic");
        boolean waiting = false;
        while(true){
            EventMessage recv_message = null;
            try {
                recv_message = consume("topicEventMessages");
            } catch (Exception e) {
                if(waiting){
                    try {
                        Thread.sleep(20); // aspettiamo per non fondere la CPU
                    } catch (Exception ee) {
                        continue; // se non ti va la wait ti meriti che ti si fonda la CPU
                    }
                } else {
                    waiting = true;
                }
                continue;
            }
            waiting = false;
            if (recv_message instanceof CreateEventMessage){
                crea((CreateEventMessage)recv_message);
            } else if (recv_message instanceof AddSeatsEventMessage) {
                aggiungi((AddSeatsEventMessage) recv_message);
            } else if (recv_message instanceof BookSeatsEventMessage) {
                prenota((BookSeatsEventMessage)recv_message);
            } else if (recv_message instanceof CloseEventMessage) {
                chiudi((CloseEventMessage) recv_message);
            } else {
                System.out.println("EVENTS: Error while handeling message");
            }
        }
    }

}
