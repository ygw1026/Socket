package com.nhnacademy.server.method.response.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;

public class WhisperResponse implements Response{
    @Override
    public String getMethod() {
        return "whisper";
    }

    @Override
    public String execute(String value) {
        if(!Session.isLogin()) {
            return "login required";
        }
        if(StringUtils.isEmpty(value)){
            return "empty message!(value is empty)";
        }
        
        String[] values = value.split(" ");
        if(values.length<2){
            return "empty message!(values < 2)";
        }

        String id = values[0];
        String message = value.substring(id.length());

        Socket client = MessageServer.getClientSocket(id);
        try{
            PrintWriter out = new PrintWriter(client.getOutputStream(), true);
            out.println(String.format("[%s] %s", Session.getCurrentId(), message));
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

        return String.format("[%s][%s] %s", getMethod(), id, message);
    }
}
