package com.nhnacademy.server.method.response.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class LoginResponseTest {
    
    static Response loginResponse;

    @BeforeEach
    void setUp() {
        loginResponse = new LoginResponse();
    }

    @Test
    @Order(0)
    @DisplayName("instance of Response")
    void constructor() {
        Assertions.assertInstanceOf(Response.class, new LoginResponse());
    }

    @Test
    @Order(1)
    @DisplayName("getMethod : login")
    void getMethod() {
        Assertions.assertEquals("login", loginResponse.getMethod());
    }

    @Test
    @Order(2)
    @DisplayName("validate")
    void validate() {
        Assertions.assertTrue(loginResponse.validate("login"));
    }

    @Test
    @Order(3)
    @DisplayName("login list")
    void execute1() {
        List<String> clientIds = List.of("marco", "nhnacademy");

        try(MockedStatic<MessageServer> mockedStatic = mockStatic(MessageServer.class)) {
            mockedStatic.when(MessageServer::getClientIds).thenReturn(clientIds);
            String actual = loginResponse.execute("list");
            Assertions.assertEquals(String.join(System.lineSeparator(), clientIds), actual);
        }
    }

    @Test
    @Order(4)
    @DisplayName("login marco")
    void execute2() {
        List<String> clientIds = List.of("nhnacademy");

        try(MockedStatic<MessageServer> mockedStatic =  mockStatic(MessageServer.class)){
            mockedStatic.when(MessageServer::getClientIds).thenReturn(clientIds);

            mockedStatic.when(() ->{
                MessageServer.addClient(anyString(), any());
            }).thenReturn(true);

            String actual = loginResponse.execute("marco");
            log.debug("actual:{}", actual);

            Assertions.assertAll(
                ()-> {
                    Assertions.assertTrue(actual.contains("success"));
                }
            );
        }
    }
}
