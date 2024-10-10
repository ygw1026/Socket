package com.nhnacademy.server.method.response.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.thread.channel.Session;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class WhoamiResponseTest {
    WhoamiResponse whoamiResponse;

    @BeforeEach
    void setUp(){
        whoamiResponse = new WhoamiResponse();
        Session.initializeId("marco");
    }

    @Test
    @DisplayName("instance of Response")
    void constructor(){
        Assertions.assertInstanceOf(Response.class, whoamiResponse);
    }

    @Test
    void getMethod() {
        Assertions.assertEquals("whoami",whoamiResponse.getMethod());
    }

    @Test
    @DisplayName("validate : true")
    void validate1(){
        Assertions.assertTrue(whoamiResponse.validate("whoami"));
    }

    @Test
    @DisplayName("validate : false")
    void validate2(){
        Assertions.assertFalse(whoamiResponse.validate("login"));
    }

    @Test
    @DisplayName("execute : whoami(\"marco\")")
    void execute() {
        String actual = whoamiResponse.execute("");
        Assertions.assertTrue(actual.contains("marco"));
    }
}
