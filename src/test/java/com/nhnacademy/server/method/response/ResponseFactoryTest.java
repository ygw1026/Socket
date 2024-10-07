package com.nhnacademy.server.method.response;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nhnacademy.server.method.response.exception.ResponseNotFoundException;
import com.nhnacademy.server.method.response.impl.EchoResponse;

class ResponseFactoryTest {
     @Test
    @DisplayName("getResponse By method")
    void getResponse() {
        Response response = ResponseFactory.getResponse("echo");
        Assertions.assertInstanceOf(EchoResponse.class, response);
    }

    @Test
    @DisplayName("getResponse by something")
    void getResponseByNotExistMethodName(){
        Assertions.assertThrows(ResponseNotFoundException.class,()->{
            ResponseFactory.getResponse("something");
        });
    }
}
