package com.nhnacademy.server.method.response;

import java.util.ArrayList;
import java.util.Objects;

import com.nhnacademy.server.method.response.exception.ResponseNotFoundException;
import com.nhnacademy.server.method.response.impl.EchoResponse;
import com.nhnacademy.server.method.response.impl.TimeResponse;

public class ResponseFactory {
    private static final ArrayList<Response> responseList = new ArrayList<>(){{
        add(new EchoResponse());
        add(new TimeResponse());
    }};

    public static Response getResponse(String method) {
        Response response = responseList.stream()
                            .filter(o -> o.validate(method))
                            .findFirst()
                            .orElse(null);
        if(Objects.isNull(response)) {
            throw new ResponseNotFoundException();
        }
        return response;
    }
}
