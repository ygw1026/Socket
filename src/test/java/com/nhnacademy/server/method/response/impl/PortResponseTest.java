package com.nhnacademy.server.method.response.impl;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.nhnacademy.server.method.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class PortResponseTest {
    
    static Thread thread1;

    static Response portResponse;

    @BeforeAll
    static void beforeAllSetup() throws InterruptedException {
        thread1 = new Thread(()-> {
            try {
                ServerSocket serverSocket = new ServerSocket(5600);
                while (!Thread.currentThread().isInterrupted()) {

                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        thread1.start();

        portResponse = new PortResponse();
        Thread.sleep(2000);
    }

    @Test
    @Order(0)
    @DisplayName("instance of Response")
    void constructor() {
       Assertions.assertInstanceOf(Response.class, new PortResponse()); 
    }

    @Test
    @Order(1)
    @DisplayName("getMethod : port")
    void getMethod() {
        Assertions.assertEquals("port", portResponse.getMethod());
    }

    @Test
    @Order(2)
    @DisplayName("validate:true")
    void validate1(){
        Assertions.assertTrue(portResponse.validate("port"));
    }

    @Test
    @Order(3)
    @DisplayName("validate:false")
    void validate2() {
        Assertions.assertFalse(portResponse.validate("doSomething"));
    }

    @Test
    @Order(4)
    @DisplayName("port 5600")
    void execute1() {
        String actual = portResponse.execute("5600");
        Assertions.assertTrue(actual.contains("5600"));
        log.debug("actual:{}", actual);
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
        thread1.interrupt();
        Thread.sleep(2000);
    }
}
