package com.nhnacademy.client.event.observer.impl;

import java.util.Objects;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.subject.EventType;

public class MessageSendObserver implements Observer{
    private final MessageAction messageAction;

    public MessageSendObserver(MessageAction messageAction) {
        if(Objects.isNull(messageAction)) {
            throw new IllegalArgumentException("messageAction is null");
        }
        this.messageAction = messageAction;
    }

    @Override
    public EventType getEventType() {
        return EventType.SEND;
    }

    @Override
    public void updateMessage(String message) {
        messageAction.execute(message);
    }
}
