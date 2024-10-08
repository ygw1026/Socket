package com.nhnacademy.client.event.subject;

import static org.mockito.ArgumentMatchers.anyString;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.function.Try;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.Mockito;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageRecvObserver;
import com.nhnacademy.client.event.observer.impl.MessageSendObserver;

class MessageSubjectTest {
    
    MessageSubject messageSubject;
    MessageAction messageAction;

    Observer sendObserver;
    Observer recvObserver;

    @BeforeEach
    void setUp() {
        messageSubject = new MessageSubject();
        messageAction = Mockito.mock(MessageAction.class);

        sendObserver = new MessageSendObserver(messageAction);
        recvObserver = new MessageRecvObserver(messageAction);

        messageSubject.register(EventType.SEND, sendObserver);
        messageSubject.register(EventType.RECV, recvObserver);
    }

    @Test
    @DisplayName("register observer")
    void register() throws Exception {
        
        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(MessageSubject.class, "observers", messageSubject);
        List<Observer> observers = (List<Observer>) readFieldValue.get();
        Assertions.assertAll(
            ()->{
                Assertions.assertEquals(2, observers.size());
            },
            ()->{
                Assertions.assertTrue(observers.contains(sendObserver));
            },
            ()->{
                Assertions.assertTrue(observers.contains(recvObserver));
            }
        );
    }

    @Test
    @DisplayName("remove observer")
    void remove() throws Exception {
        Try<Object> readFieldValue = ReflectionUtils.tryToReadFieldValue(MessageSubject.class, "observers", messageSubject);
        List<Observer> observers = (List<Observer>) readFieldValue.get();

        observers.remove(sendObserver);

        Assertions.assertAll(
            ()->{
                Assertions.assertEquals(1, observers.size());
            },
            ()->{
                Assertions.assertFalse(observers.contains(sendObserver));
            },
            ()->{
                Assertions.assertTrue(observers.contains(recvObserver));
            }
        );
    }

    @Test
    @DisplayName("notify - sendObserver")
    void notifySendObserver() {
        messageSubject.sendMessage("echo hello");
        Mockito.verify(messageAction, Mockito.times(1)).execute(anyString());
    }

    @Test
    @DisplayName("notify - recvObserver")
    void notifyRecvObservers() {
        messageSubject.receiveMessage("echo hello");
        Mockito.verify(messageAction, Mockito.times(1)).execute(anyString());
    }
}
