package com.nhnacademy.server.method.response;

import java.util.ArrayList;
import java.util.Objects;

import com.nhnacademy.server.method.response.exception.ResponseNotFoundException;
import com.nhnacademy.server.method.response.impl.BroadCastResponse;
import com.nhnacademy.server.method.response.impl.EchoResponse;
import com.nhnacademy.server.method.response.impl.LoginResponse;
import com.nhnacademy.server.method.response.impl.PortResponse;
import com.nhnacademy.server.method.response.impl.TimeResponse;

public class ResponseFactory {
    private static final ArrayList<Response> responseList = new ArrayList<>(){{
        add(new EchoResponse());
        add(new TimeResponse());
        add(new PortResponse());
        add(new LoginResponse());
        add(new BroadCastResponse());
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
