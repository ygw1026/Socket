package com.nhnacademy.server.method.response;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import org.reflections.Reflections;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseFactory {
    private static final ArrayList<Response> responseList = new ArrayList<>();

    static {
        Reflections reflections = new Reflections("com.nhnacademy.server");
        Set<Class<? extends Response>> classes = reflections.getSubTypesOf(Response.class);

        for (Class<? extends Response> clazz : classes) {
            try {
                Response response = clazz.getDeclaredConstructor().newInstance();
                log.debug("response-factory init : instance : {}", response.getClass().getName());
                responseList.add(response);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Response getResponse(String method) {
        Response response = responseList.stream()
                            .filter(o -> o.validate(method))
                            .findFirst()
                            .orElse(null);
        if(Objects.isNull(response)) {
            log.error("response not found : {}", method);
        }
        return response;
    }
}
