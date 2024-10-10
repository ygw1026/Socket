package com.nhnacademy.server.method.response.impl;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class WhisperResponseTest {
    Response whisperResponse;

    @BeforeEach
    void setUp(){
        whisperResponse = new WhisperResponse();
        Session.initializeId("marco");
    }

    @Test
    @DisplayName("instance of Response")
    void constructor(){
        Assertions.assertInstanceOf(Response.class, whisperResponse);
    }

    @Test
    void getMethod() {
        Assertions.assertEquals("whisper",whisperResponse.getMethod());
    }

    @Test
    @DisplayName("validate : true")
    void validate1(){
        Assertions.assertTrue(whisperResponse.validate("whisper"));
    }

    @Test
    @DisplayName("validate : false")
    void validate2(){
        Assertions.assertFalse(whisperResponse.validate("login"));
    }

    @Test
    @DisplayName("whisper nhnacademy hello")
    void execute1() throws IOException {
        Socket client = Mockito.mock(Socket.class);
        Mockito.when(client.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        try(MockedStatic<MessageServer> mockedStatic = mockStatic(MessageServer.class)){
            mockedStatic.when(()->{
                MessageServer.getClientSocket(anyString());
            }).thenReturn(client);
            String message = "hello";
            String actual = whisperResponse.execute(String.format("nhnacademy %s", message));
            Assertions.assertTrue(actual.contains(message));
            log.debug("actual:{}",actual);
        }
    }

    @Test
    @DisplayName("whisper nhnacademy hello java")
    void execute2() throws IOException {
        Socket client = Mockito.mock(Socket.class);
        Mockito.when(client.getOutputStream()).thenReturn(new ByteArrayOutputStream());

        try(MockedStatic<MessageServer> mockedStatic = mockStatic(MessageServer.class)){
            mockedStatic.when(()->{
                MessageServer.getClientSocket(anyString());
            }).thenReturn(client);
            String message = "hello java";
            String actual = whisperResponse.execute(String.format("nhnacademy %s", message));
            Assertions.assertTrue(actual.contains(message));
            log.debug("actual:{}",actual);
        }
    }
}
