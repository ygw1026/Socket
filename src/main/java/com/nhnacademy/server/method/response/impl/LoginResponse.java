package com.nhnacademy.server.method.response.impl;

import java.util.List;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;

public class LoginResponse implements Response{
    @Override
    public String getMethod() {
        return "login";
    }

    @Override
    public String execute(String value) {
        if (value.equals("list")) {
            List<String> ids = MessageServer.getClientIds();
            return ids.size() > 0 ? String.join(System.lineSeparator(), ids) : "empty";
        }

        boolean loginSuccess = MessageServer.addClient(value, Session.getCurrentSocket());

        if (loginSuccess) {
            Session.initializeId(value);
            return "login success!";
        }

        return "login fail";
    }
}
