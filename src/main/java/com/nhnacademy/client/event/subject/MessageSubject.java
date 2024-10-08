package com.nhnacademy.client.event.subject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.nhnacademy.client.event.observer.Observer;

public class MessageSubject implements Subject{
    
    private final List<Observer> observers;

    public MessageSubject() {
        observers = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void register(EventType eventType, Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(EventType eventType, Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventType eventType, String message) {
        for (Observer observer : observers) {
            if(observer.validate(eventType)) {
                observer.updateMessage(message);
            }
        }
    }
}
