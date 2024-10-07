package com.nhnacademy.server.method.parser;

import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MethodParserTest {
    
    @ParameterizedTest
    @MethodSource("methodValuePairs")
    void parseTest(String message, String method, String value) {
        MethodParser.MethodAndValue methodAndValue = MethodParser.parse(message);
        log.debug("message:{}, method:{}, value:{}", message, method, value);
        Assertions.assertAll(
            () -> {
                Assertions.assertEquals(method, methodAndValue.getMethod());
            },
            () -> {
                Assertions.assertEquals(value, methodAndValue.getValue());
            }
        );
    }

    static Stream<Arguments> methodValuePairs() {
        return Stream.of(
            Arguments.of("echo hello", "echo","hello"),
            Arguments.of("echo nhnacademy", "echo","nhnacademy"),
            Arguments.of("echo", "echo",""),
            Arguments.of("echo 엔에이치엔아카데미", "echo","엔에이치엔아카데미")
        );
    }

    @ParameterizedTest
    @MethodSource("emptyMessages")
    void parseByEmptyMessageTest(String message) {
        MethodParser.MethodAndValue methodAndValue = MethodParser.parse(message);
        Assertions.assertNull(methodAndValue);
    }

    static Stream<Arguments> emptyMessages() {
        return Stream.of(
                Arguments.of( ""),
                Arguments.of("   ")
        );
    }
}
