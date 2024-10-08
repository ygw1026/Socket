package com.nhnacademy.client.event.observer.impl;

import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.subject.EventType;

class MessageSendObserverTest {
    
    MessageSendObserver messageSendObserver;
    MessageAction messageAction;

    @BeforeEach
    void setUp() {
        messageAction = Mockito.mock(MessageAction.class);
        messageSendObserver = new MessageSendObserver(messageAction);
    }

    @Test
    @DisplayName("messageAction is null")
    void constructor1() {
        Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            new MessageRecvObserver(null);
        });
    }

    @Test
    @DisplayName("instance of Observer")
    void constructor2() {
        Assertions.assertInstanceOf(Observer.class, messageSendObserver);
    }

    @Test
    void getEventType() {
        Assertions.assertEquals(EventType.SEND, messageSendObserver.getEventType());
    }

    @Test
    void updateMessage() {
        messageSendObserver.updateMessage("echo hello");
        Mockito.verify(messageAction, Mockito.times(1)).execute(anyString());
    }
}
