package com.nhnacademy.server.method.response.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nhnacademy.server.method.response.Response;

class EchoResponseTest {
    
    Response echoResponse;

    @BeforeEach
    void setUp() {
        echoResponse = new EchoResponse();
    }

    @Test
    @DisplayName("get method")
    void getResponse() {
        Assertions.assertEquals("echo", echoResponse.getMethod());
    }

    @Test
    @DisplayName("validate echoResponse, echo method에 대한 응답을 구현한 구현체인지 검증 합니다.")
    void validate() {
        Assertions.assertTrue(echoResponse.validate("echo"));
    }

    @Test
    @DisplayName("execute")
    void execute() {
        String actual = echoResponse.execute("hello");
        Assertions.assertEquals("hello", actual);
    }
}
