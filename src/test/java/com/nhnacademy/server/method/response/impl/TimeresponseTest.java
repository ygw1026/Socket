package com.nhnacademy.server.method.response.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nhnacademy.server.method.response.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TimeresponseTest {

    Response response;
    
    @BeforeEach
    void setUp() {
        response = new TimeResponse();
    }

    @Test
    void getMethod() {
        Assertions.assertEquals("time", response.getMethod());
    }

    @Test
    void validate() {
        Assertions.assertTrue(response.validate("time"));
    }

    @Test
    @DisplayName("pattern : yyyy")
    void execute1() {
        String pattern = "yyyy";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String actual = LocalDateTime.now().format(dateTimeFormatter);
        log.debug("pattern:{}, actual:{}", pattern, actual);
        Assertions.assertEquals(actual, response.execute(pattern));
    }

    @Test
    @DisplayName("pattern : yyyy-MM-dd")
    void execute2() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String actual = LocalDateTime.now().format(dateTimeFormatter);
        log.debug("pattern:{}, actual:{}", pattern, actual);
        Assertions.assertEquals(actual, response.execute(pattern));
    }

    @Test
    @DisplayName("pattern : yyyy-MM-dd HH:mm")
    void execute3() {
        String pattern = "yyyy-MM-dd HH:mm";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        String actual = LocalDateTime.now().format(dateTimeFormatter);
        log.debug("pattern:{}, actual:{}", pattern, actual);
        Assertions.assertEquals(actual, response.execute(pattern));
    }
}
