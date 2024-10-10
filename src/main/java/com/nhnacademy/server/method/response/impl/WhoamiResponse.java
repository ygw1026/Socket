package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.thread.channel.Session;

public class WhoamiResponse implements Response{
    @Override
    public String getMethod() {
        return "whoami";
    }

    @Override
    public String execute(String value) {
        if(!Session.isLogin()){
            return "login required!";
        }
        return String.format("my id is [%s]", Session.getCurrentId());
    }
}       
