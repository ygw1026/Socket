package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;

public class EchoResponse implements Response {
    
    private final static String METHOD = "echo";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        return value;
    }
}
