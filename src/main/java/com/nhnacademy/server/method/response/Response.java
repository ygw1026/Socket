package com.nhnacademy.server.method.response;

public interface Response {
    String getMethod();
    String execute(String value);

    default boolean validate(String method) {
        return getMethod().equals(method);
    }
}
