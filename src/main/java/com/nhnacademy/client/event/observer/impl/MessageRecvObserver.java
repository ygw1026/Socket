package com.nhnacademy.client.event.observer.impl;

import java.util.Objects;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.subject.EventType;

public class MessageRecvObserver implements Observer{
    private final MessageAction messageAction;

    public MessageRecvObserver (MessageAction messageAction) {
        if (Objects.isNull(messageAction)) {
            throw new IllegalArgumentException("messageAction is Null");
        }
        this.messageAction = messageAction;
    }

    @Override
    public EventType getEventType() {
        return EventType.RECV;
    }

    @Override
    public void updateMessage(String message) {
        messageAction.execute(message);
    }
}
