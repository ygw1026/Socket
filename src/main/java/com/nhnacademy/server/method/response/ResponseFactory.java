package com.nhnacademy.server.method.response;

import java.util.ArrayList;
import java.util.Objects;

import com.nhnacademy.server.method.response.exception.ResponseNotFoundException;
import com.nhnacademy.server.method.response.impl.EchoResponse;

public class ResponseFactory {
    private static final ArrayList<Response> responseList = new ArrayList<>(){{
        add(new EchoResponse());
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
